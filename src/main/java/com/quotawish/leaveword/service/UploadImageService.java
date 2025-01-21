package com.quotawish.leaveword.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {

    String uploadQNImg(MultipartFile file);

    String getPrivateFile(String fileKey);

    boolean removeFile(String bucketName, String fileKey);

}
