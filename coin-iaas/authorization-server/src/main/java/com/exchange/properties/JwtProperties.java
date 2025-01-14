package com.exchange.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Jwt属性
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/13 19:55
 */
@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Long accessTokenValiditySeconds;
    private Long refreshTokenValiditySeconds;
}
