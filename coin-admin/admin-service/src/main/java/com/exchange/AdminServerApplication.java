package com.exchange;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 19:47
 */
@SpringBootApplication // exclude = {SecurityAutoConfiguration.class} 不需要springSecurity的web安全功能
//@EnableDubbo // 启用dubbo注解
@MapperScan(basePackages = "com.exchange.mapper")
public class AdminServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }
}
