package com.exchange;

import com.exchange.properties.JwtProperties;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/3 20:40
 */
@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
//@EnableDubbo
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
