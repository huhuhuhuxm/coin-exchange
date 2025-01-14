package com.exchange.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * url白名单
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/14 13:31
 */
@Data
@ConfigurationProperties(prefix = "url")
public class UrlWhiteListProperties {
    private Set<String> UrlWhiteList;
}
