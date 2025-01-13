package com.exchange.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * UserLoginDTO
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/10 20:36
 */
@Data
public class UserLoginDTO {
    private String username;
    private String password;
}
