package com.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.exchange.constants.SysConstants;
import com.exchange.dto.UserDTO;
import com.exchange.entity.SysRole;
import com.exchange.entity.SysUser;
import com.exchange.entity.SysUserRole;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.mapper.SysUserRoleMapper;
import com.exchange.model.R;
import com.exchange.service.SysUserRoleService;
import com.exchange.service.SysUserService;
import com.exchange.mapper.SysUserMapper;
import com.mysql.cj.log.Log;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
* @author hxm
* @description 针对表【sys_user(平台用户)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    AuthorizationServiceFeign authorizationServiceFeign;
    SysUserRoleService sysUserRoleService;
    ApplicationContext applicationContext;  // 注入 ApplicationContext
    SysUserMapper sysUserMapper;



    @Override
    public Long getUserId() {
        // 使用 RequestContextHolder 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(SysConstants.AUTHORIZATION);
        // 远程调用获取userId
        R userIdFromToken = authorizationServiceFeign.getUserIdFromToken(token);
        // 判断远程调用是否成功 如果内容是异常 则在抛出异常
        if (!ResultCodeEnum.SUCCESS.getCode().equals(userIdFromToken.getCode())) {
            throw new BusinessException(userIdFromToken.getCode(), userIdFromToken.getMsg());
        }
        Long userId = Long.valueOf(userIdFromToken.getData().toString());
        return userId;
    }


    /**
     * 添加员工
     * @param userDTO
     * @return
     */
    @Transactional
    @Override
    public boolean addUser(UserDTO userDTO) {
        String[] roleIds = userDTO.getRole_strings().split(",");
        // 构造SysUser
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        // 把用户密码进行加密
        String encodePassword = new BCryptPasswordEncoder().encode(sysUser.getPassword());
        log.info("encodePassword：{}", encodePassword);
        sysUser.setPassword(encodePassword);
        // 添加用户
        boolean isSave = super.save(sysUser);
        if (isSave) {
            // 这是为了防止事务失效 Spring 的 AOP 事务代理不会对 本类内部的直接方法调用 进行代理，这意味着事务管理无法应用于内部调用的 updateUserRoles 方法。
            SysUserServiceImpl proxy = applicationContext.getBean(SysUserServiceImpl.class);
            // 给新用户分配角色
            proxy.updateUserRoles(sysUser.getId(), roleIds, sysUserRoleService);
        }
        return isSave;
    }


    /**
     * 编辑员工
     * @param userDTO
     * @return
     */
    @Transactional
    @Override
    public boolean updateUser(UserDTO userDTO) {
        String[] roleIds = userDTO.getRole_strings().split(",");
        Long userId = userDTO.getId();
        // 先判该用户是否分配角色
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if ( sysUserRoles == null ||sysUserRoles.isEmpty()) return false;
        // 先删除该用户绑定的权限
        boolean isRemove = sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (!isRemove) return false;
        // 更新用户信息
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDTO, sysUser);
        boolean isUpdate = this.updateById(sysUser);
        if (!isUpdate) return false;
        // 更新用户权限
        // 这是为了防止事务失效 Spring 的 AOP 事务代理不会对 本类内部的直接方法调用 进行代理，这意味着事务管理无法应用于内部调用的 updateUserRoles 方法。
        SysUserServiceImpl proxy = applicationContext.getBean(SysUserServiceImpl.class);
        // 给新用户分配角色
        boolean is = proxy.updateUserRoles(sysUser.getId(), roleIds, sysUserRoleService);
        return is;
    }

    /**
     * 批量删除员工
     * @param userIds
     * @return
     */
    @Transactional
    @Override
    public boolean deleteUsers(List<Long> userIds) {
        // 删除员工相关联的角色
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, userIds));
        // 删除员工
        int isDelete = sysUserMapper.deleteByIds(userIds);
        return SqlHelper.retBool(isDelete);
    }


    /**
     * 更新用户权限
     * @param userId
     * @param roleIds
     * @return
     */
    private boolean updateUserRoles(Long userId, String[] roleIds, SysUserRoleService sysUserRoleService) {
        List<SysUserRole> userRoles = new ArrayList<>();
        for (String roleId : roleIds) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(Long.valueOf(roleId));
            // TODO 后续写个过滤器 得到当前请求用户的基本信息
//            sysUserRole.setCreateBy();
//            sysUserRole.setModifyBy();
            userRoles.add(sysUserRole);
        }
        return sysUserRoleService.saveBatch(userRoles);
    }

}




