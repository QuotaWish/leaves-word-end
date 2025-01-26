package com.quotawish.leaveword.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.quotawish.leaveword.config.QiniuConfig;
import com.quotawish.leaveword.service.UploadImageService;
import com.quotawish.leaveword.utils.StringUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class UploadImageServiceImpl implements UploadImageService {

    private QiniuConfig qiNiuYunConfig;

    // 七牛文件上传管理器
    private UploadManager uploadManager;
    //上传的token
    private String token;
    // 七牛认证管理
    private Auth auth;

    private BucketManager bucketManager;

    public UploadImageServiceImpl(QiniuConfig qiNiuYunConfig) {
        this.qiNiuYunConfig = qiNiuYunConfig;
        init();
    }

    private void init() {
        // 构造一个带指定Zone对象的配置类, 注意这里的Zone.zone0需要根据主机选择
        uploadManager = new UploadManager(new Configuration(Zone.zone2()));
        auth = Auth.create(qiNiuYunConfig.getAccessKey(), qiNiuYunConfig.getSecretKey());
        // 根据命名空间生成的上传token
        bucketManager = new BucketManager(auth, new Configuration(Zone.zone2()));
        token = auth.uploadToken(qiNiuYunConfig.getBucketName());
    }

    /*
     * 上传文件
     * @Param [file, key]
     * @return java.lang.String
     **/
    @SneakyThrows
    @Override
    public String uploadQNImg(MultipartFile file) {
        // 获取文件的名称
        String fileName = file.getOriginalFilename();

        FileInputStream inputStream = (FileInputStream) file.getInputStream();

        return uploadFile(fileName, inputStream);
    }

    @SneakyThrows
    @Override
    public String uploadFile(String name, InputStream stream) {
        String imgName = StringUtil.getRandomImgName(name);

        Response res = uploadManager.put(stream, imgName, token, null, null);
        if (!res.isOK()) {
            throw new RuntimeException("上传七牛出错：" + res.toString());
        }
        // 解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);

        System.out.println(putRet.key + " | " + putRet.hash);

        // 直接返回外链地址
        return getPrivateFile(imgName);
    }

    /**
     * 获取私有空间文件
     *
     * @param fileKey
     * @return
     */
    @Override
    public String getPrivateFile(String fileKey) {
        String encodedFileName = null;
        String finalUrl = null;
        try {
            encodedFileName = URLEncoder.encode(fileKey, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", this.qiNiuYunConfig.getUrl(), encodedFileName);
            long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    /*
     * 根据空间名、文件名删除文件
     * @Param [bucketName, fileKey]
     * @return boolean
     **/
    @Override
    public boolean removeFile(String bucketName, String fileKey) {
        try {
            bucketManager.delete(bucketName, fileKey);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return true;
    }

}
