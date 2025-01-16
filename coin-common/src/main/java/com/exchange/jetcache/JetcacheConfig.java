package com.exchange.jetcache;

import com.alicp.jetcache.anno.Cached;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 14:28
 */
@Configuration
@EnableMethodCache(basePackages = "com.exchange.service.impl")
public class JetcacheConfig {

}
