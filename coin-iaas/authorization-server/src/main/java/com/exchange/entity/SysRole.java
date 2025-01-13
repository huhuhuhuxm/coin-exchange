package com.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色表
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 12:20
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId
    private Long id; // 主键id
    private String name; // 名称
    private String code; // 代码
    private String description; // 描述
    private Long createBy; // 创建人
    private Long modifyBy; // 修改人
    @TableLogic(value = "1", delval = "0")
    private Integer status; // 状态0:禁用 1:启用
    private LocalDateTime created; // 创建时间
    private LocalDateTime lastUpdateTime; // 修改时间
}
