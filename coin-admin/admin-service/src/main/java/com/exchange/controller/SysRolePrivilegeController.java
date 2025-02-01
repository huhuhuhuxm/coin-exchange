package com.exchange.controller;

import com.exchange.entity.SysMenu;
import com.exchange.model.R;
import com.exchange.service.SysRolePrivilegeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色权限配置
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/31 21:49
 */
@Slf4j
@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRolePrivilegeController {

    SysRolePrivilegeService sysRolePrivilegeService;

    /**
     * 查询角色的权限列表
     * @return
     */
    @GetMapping("/roles_privileges")
    public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId) {
        List<SysMenu> sysMenus = sysRolePrivilegeService.findSysMenuAndPrivileges(roleId);
        return R.ok(sysMenus);
    }

}
