package com.exchange.vo;

import lombok.Data;

/**
 * 授权TokenVO
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/10 20:24
 */
@Data
public class AuthTokenVO {
    private String accessToken;
    private String refreshToken;
}
