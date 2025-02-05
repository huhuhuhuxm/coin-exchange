package com.exchange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.entity.SysRole;
import com.exchange.model.R;
import com.exchange.service.SysRoleService;
import com.exchange.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/27 14:40
 */
@Slf4j
@RestController
@RequestMapping("/roles")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated // 开启注解校验
public class SysRoleController {

    SysRoleService sysRoleService;
    SysUserService sysUserService;


    /**
     * 角色分页查询
     * @param current
     * @param size
     * @param name
     * @return
     */
    @Operation(description = "角色分页查询")
    @GetMapping
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<Page<SysRole>> findByPage(@RequestParam Long current, @RequestParam Long size, @RequestParam(required = false) String name) {
        Page<SysRole> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.addOrder(OrderItem.desc("last_update_time"));
        // 开始查询
        Page<SysRole> sysRolePage = sysRoleService.page(page,
                new LambdaQueryWrapper<SysRole>().like(StringUtils.hasText(name),
                        SysRole::getName,
                        name));
        return R.ok(sysRolePage);
    }

    /**
     * 新增角色
     * @return
     */
    @Operation(description = "新增角色")
    @PostMapping
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<String> addRole(@RequestBody SysRole sysRole) {
        Long userId = sysUserService.getUserId();
        sysRole.setCreateBy(userId);
        sysRole.setModifyBy(userId);
        boolean isSave = sysRoleService.save(sysRole);
        if (isSave) {
            return R.ok("新增成功！！！");
        }
        return R.fail("新增失败！！！");
    }

    /**
     * 根据相关id删除角色
     * @param ids
     * @return
     */
    @Operation(description = "根据相关id删除角色")
    @PostMapping("/delete")
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<String> deleteRolesByIds(@RequestBody @NotNull(message = "数组不能为null")
                                          @NotEmpty(message = "数组不能为空且长度必须大于0") String[] ids) {
        // TODO 后续修改 比如该角色绑定了用户择不能 删除 角色如果删除必须删除相关表中的数据 避免脏数据
        boolean isDelete = sysRoleService.removeByIds(Arrays.stream(ids).toList());
        if (isDelete) {
            return R.ok("删除成功！！！");
        }
        return R.fail("删除失败！！！");
    }


    /**
     * 根据id获取角色信息
     * @param roleId
     * @return
     */
    @Operation(description = "根据id获取角色信息")
    @GetMapping("/{roleId}")
    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN')")
    public R<SysRole> getRoleInfoById(@PathVariable Long roleId) {
        // 开始查询
        SysRole sysRole = sysRoleService.getById(roleId);
        return R.ok(sysRole);
    }

}
