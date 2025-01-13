package com.exchange.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 权限表
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 14:28
 */
@Data
public class SysPrivilege {
    private Long id; // 主键id
    private Long menuId; // 所属菜单id
    private String name; // 功能点描述
    private String description; // 功能描述
    private String url;
    private String method;
    private Long createBy; // 创建人
    private Long modifyBy; // 修改人
    private LocalDateTime created; // 创建时间
    private LocalDateTime lastUpdateTime; // 修改时间
}
