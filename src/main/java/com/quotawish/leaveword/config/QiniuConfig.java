package com.quotawish.leaveword.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class QiniuConfig {
    /**
     * 七牛域名domain
     */
    @Value("${oss.qiniu.url}")
    private String url;
    /**
     * 七牛ACCESS_KEY
     */
    @Value("${oss.qiniu.accessKey}")
    private String AccessKey;
    /**
     * 七牛SECRET_KEY
     */
    @Value("${oss.qiniu.secretKey}")
    private String SecretKey;
    /**
     * 七牛空间名
     */
    @Value("${oss.qiniu.bucket}")
    private String BucketName;
}
