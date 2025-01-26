package com.quotawish.leaveword.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface UploadImageService {

    String uploadQNImg(MultipartFile file);

    String uploadFile(String name, InputStream stream);

    String getPrivateFile(String fileKey);

    boolean removeFile(String bucketName, String fileKey);

}
