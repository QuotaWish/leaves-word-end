package com.quotawish.leaveword.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Data
@Configuration
public class CozeConfig {

    @Value("${coze.oauth.pat}")
    private String pat;

    @Value("${coze.workflow}")
    private String workflowId;

    @Value("${coze.addon}")
    private Map<String, String> addon;


    @Value("${coze.addon.supply}")
    private String supplyId;

//    /**
//     * OAuth重定向URI
//     */
//    @Value("${coze.web.oauth.redirectUri}")
//    private String redirectUri;
//
//    /**
//     * OAuth客户端密钥
//     */
//    @Value("${coze.web.oauth.clientSecret}")
//    private String clientSecret;
//
//    /**
//     * OAuth客户端ID
//     */
//    @Value("${coze.web.oauth.clientId}")
//    private String clientId;

}
