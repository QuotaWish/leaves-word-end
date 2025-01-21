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
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
    @Override
    public String uploadQNImg(MultipartFile file) {
        try {
            // 获取文件的名称
            String fileName = file.getOriginalFilename();

            // 使用工具类根据上传文件生成唯一图片名称
            String imgName = StringUtil.getRandomImgName(fileName);

            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            // 上传图片文件
            Response res = uploadManager.put(inputStream, imgName, token, null, null);
            if (!res.isOK()) {
                throw new RuntimeException("上传七牛出错：" + res.toString());
            }
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);

            // 直接返回外链地址
            return getPrivateFile(imgName);
        } catch (QiniuException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
