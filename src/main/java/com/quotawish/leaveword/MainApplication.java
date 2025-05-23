package com.quotawish.leaveword;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/TalexDreamSoul">Tds</a>
 */
@SpringBootApplication()
@MapperScan("com.quotawish.leaveword.mapper")
@EnableAsync
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
