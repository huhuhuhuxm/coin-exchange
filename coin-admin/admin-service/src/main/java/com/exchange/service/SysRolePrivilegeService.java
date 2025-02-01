package com.exchange.service;

import com.exchange.entity.SysMenu;
import com.exchange.entity.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hxm
* @description 针对表【sys_role_privilege(角色权限配置)】的数据库操作Service
* @createDate 2025-01-19 12:41:08
*/
public interface SysRolePrivilegeService extends IService<SysRolePrivilege> {

    /**
     * 根据角色id查询菜单和权限
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuAndPrivileges(Long roleId);
}
