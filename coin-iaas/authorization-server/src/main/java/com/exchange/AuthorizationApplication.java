package com.exchange;

import com.exchange.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/3 20:40
 */
@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class})
@EnableFeignClients
public class AuthorizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }
}
