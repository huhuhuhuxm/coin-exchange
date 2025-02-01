package com.exchange.service;

import com.exchange.entity.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hxm
* @description 针对表【sys_privilege(权限配置)】的数据库操作Service
* @createDate 2025-01-19 12:41:08
*/
public interface SysPrivilegeService extends IService<SysPrivilege> {

    /**
     * 查询出所有权限并且标记角色拥有对应权限
     * @param sysPrivilegeIds
     * @return
     */
    List<SysPrivilege> getAllPrivilege(List<Long> sysPrivilegeIds);
}
