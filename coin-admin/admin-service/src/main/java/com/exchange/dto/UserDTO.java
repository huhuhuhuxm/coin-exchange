package com.exchange.dto;

import com.exchange.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 用户DTO
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/4 21:38
 */
@Data
public class UserDTO  extends SysUser {

    /**
     * 角色id列表
     */
    private String role_strings;
}
