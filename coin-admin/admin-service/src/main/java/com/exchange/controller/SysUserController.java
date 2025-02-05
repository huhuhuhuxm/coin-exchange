package com.exchange.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exchange.dto.UserDTO;
import com.exchange.entity.SysUser;
import com.exchange.entity.SysUserRole;
import com.exchange.model.R;
import com.exchange.service.SysUserRoleService;
import com.exchange.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 员工管理相关api
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/4 19:59
 */
@Slf4j
@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserController {

    SysUserService sysUserService;

    SysUserRoleService sysUserRoleService;

    /**
     * 查询员工分页
     * @param current 当前页
     * @param size 每页条数
     * @return
     */
    @Operation(description = "查询员工分页")
    @GetMapping
    public R<Page<UserDTO>> findByPage(@RequestParam Long current, @RequestParam Long size, @RequestParam(value = "fullname", required = false) String name, @RequestParam(required = false) String mobile) {
        Page<SysUser> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> sysUserPage = sysUserService.page(page,
                new LambdaQueryWrapper<SysUser>()
                        .like(StringUtils.hasText(name), SysUser::getUsername, name)
                        .like(StringUtils.hasText(mobile), SysUser::getMobile, mobile));

        // 把sysUserPage转换成userDTOPage
        List<UserDTO> records = sysUserPage.getRecords().stream().map(sysUser -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(sysUser, userDTO);
            userDTO.setPassword("");
            return userDTO;
        }).toList();
        Page<UserDTO> userDTOPage = new Page<>();

        // 剩下复制分页信息 但是排除records属性
        BeanUtils.copyProperties(sysUserPage, userDTOPage, "records");
        userDTOPage.setRecords(records);
        if (!CollectionUtils.isEmpty(records)) {
            for (UserDTO record : records) {
                // 查询每个用户对应的角色列表
                List<SysUserRole> roles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
                if (!CollectionUtils.isEmpty(roles)) {
                    record.setRole_strings(roles.stream()
                            .map(role -> role.getRoleId().toString()).
                            collect(Collectors.joining(",")));
                }
            }
        }
        return R.ok(userDTOPage);
    }

    /**
     * 添加员工
     * @param userDTO
     * @return
     */
    @Operation(description = "添加员工")
    @PostMapping
    public R<String> addUser(@RequestBody UserDTO userDTO) {
        boolean isAdd = sysUserService.addUser(userDTO);
        if (isAdd) {
           return R.ok("添加成功！！！");
        }
        return R.fail("添加失败！！！");
    }


    /**
     * 编辑员工
     * @param userDTO
     * @return
     */
    @Operation(description = "编辑员工")
    @PatchMapping
    public R<String> updateUser(@RequestBody UserDTO userDTO) {
        boolean isUpdate = sysUserService.updateUser(userDTO);
        if (isUpdate) {
            return R.ok("编辑成功！！！");
        }
        return R.fail("编辑失败！！！");
    }

    /**
     * 批量删除员工
     * @param userIds
     * @return
     */
    @Operation(description = "批量删除员工")
    @PostMapping("/delete")
    public R<String> deleteUsers(@RequestBody List<Long> userIds) {
        boolean isDelete = sysUserService.deleteUsers(userIds);
        if (isDelete) {
           return R.ok("删除成功！！！");
        }
        return R.fail("删除失败！！！");
    }
}
