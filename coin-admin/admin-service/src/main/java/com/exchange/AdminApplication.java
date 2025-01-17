package com.exchange;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 19:47
 */
@SpringBootApplication // exclude = {SecurityAutoConfiguration.class} 不需要springSecurity的web安全功能
//@EnableDubbo // 启用dubbo注解
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
