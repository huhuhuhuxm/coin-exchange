package com.exchange;

import com.exchange.properties.UrlWhiteListProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/2 19:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({UrlWhiteListProperties.class})
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
