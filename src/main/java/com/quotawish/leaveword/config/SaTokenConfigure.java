package com.quotawish.leaveword.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.logging.Logger;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**");

        registry.addInterceptor(new SaInterceptor(handle -> {

            SaRouter.match("/**")
                    .notMatch(SaHttpMethod.OPTIONS)
                    .notMatch("/user/**")
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }

    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .setBeforeAuth(obj -> {

                    SaRequest request = SaHolder.getRequest();
                    String origin = request.getHeader("Origin");
                    if (origin == null) {
                        origin = request.getHeader("Referer");
                    }

                    if (origin == null) {
                        origin = "*";
                    }

                    System.out.println("origin: " + origin);

                    SaHolder.getResponse()
                            .setHeader("Access-Control-Allow-Credentials", "true")
                            .setHeader("Access-Control-Allow-Origin", origin)
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
                            .setHeader("Access-Control-Allow-Headers", "leaf, content-type, authorization, baggage, sentry-trace")
                            .setHeader("Access-Control-Max-Age", "3600")
                    ;

                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .back();
                })
                ;
    }

}
