package com.exchange.service;

import com.exchange.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author hxm
* @description 针对表【sys_role(角色)】的数据库操作Service
* @createDate 2025-01-19 12:41:08
*/
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据userId查询出roleCode
     * @param userId
     * @return
     */
    boolean isAdmin(Long userId);
}
