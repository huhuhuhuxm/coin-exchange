package com.exchange.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 角色和权限数据DTO
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/4 15:25
 */
@Data
public class RolePrivilegesParamDTO {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限列表
     */
    private List<Long> privilegeIds = Collections.emptyList();
}
