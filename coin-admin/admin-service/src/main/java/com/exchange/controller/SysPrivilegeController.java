package com.exchange.controller;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.exchange.entity.SysPrivilege;
import com.exchange.model.R;
import com.exchange.service.SysPrivilegeService;
import com.exchange.service.SysUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 权限管理相关接口
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/25 16:21
 */
@Slf4j
@RestController
@RequestMapping("/privileges")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPrivilegeController {

    SysPrivilegeService sysPrivilegeService;

    SysUserService sysUserService;


    /**
     * 查询权限配置列表
     * @param current
     * @param size
     * @return
     */
    @GetMapping
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<Page<SysPrivilege>> findByPage(@RequestParam Long current, @RequestParam Long size) {
        Page<SysPrivilege> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        // 查询是时候最近新增的优先显示
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);
    }

    /**
     * 新增系统权限
     * @param sysPrivilege
     * @return
     */
    @PostMapping
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R addPrivilege(@RequestBody @Validated SysPrivilege sysPrivilege) {
        // 新增操作时候，给一些对象填充一些信息
        Long userId = sysUserService.getUserId();
        sysPrivilege.setCreateBy(userId);
        boolean isSave = sysPrivilegeService.save(sysPrivilege);
        if (isSave) {
            return R.ok("新增成功！！！");
        }
        return R.fail("新增失败！！！");
    }

    /**
     * 修改系统权限
     * @return
     */
    @PatchMapping
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<String> updatePrivilege(@RequestBody @Validated SysPrivilege sysPrivilege) {
        Long userId = sysUserService.getUserId();
        sysPrivilege.setModifyBy(userId);
        boolean isUpdate = sysPrivilegeService.updateById(sysPrivilege);
        if (isUpdate) {
            return R.ok("修改成功！！！");
        }
        return R.fail("修改失败！！！");
    }

}
