//package com.quotawish.leaveword.utils;
//
//import cn.hutool.core.io.IoUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.tomcat.util.http.fileupload.FileItem;
//import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//@Slf4j
//public class MultipartFileUtil {
//
//
//    private MultipartFileUtil() { }
//
//
//    public static MultipartFile toMultipartFile(byte[] bytes, String fileName, String contentType) {
//        final FileItem fileItem = createFileItem(new ByteArrayInputStream(bytes), fileName,contentType);
//        return new CommonsMultipartFile(fileItem);
//    }
//
//    private static FileItem createFileItem(InputStream is, String fileName, String contentType) {
//        return createFileItem(is, "file", fileName,contentType);
//    }
//
//    private static FileItem createFileItem(InputStream is, String fieldName, String fileName,String contentType) {
//        final DiskFileItemFactory fac = new DiskFileItemFactory(10240, null);
//        FileItem fileItem = fac.createItem(fieldName, contentType, true, fileName);
//        final OutputStream fileItemOutStream;
//        try {
//            fileItemOutStream = fileItem.getOutputStream();
//        } catch (IOException e) {
//            log.error("获取FileItem输出流异常：{}", e.getMessage(), e);
//            throw new RuntimeException("系统异常");
//        }
//
//        IoUtil.copy(is, fileItemOutStream);
//        return fileItem;
//    }
//}
//
