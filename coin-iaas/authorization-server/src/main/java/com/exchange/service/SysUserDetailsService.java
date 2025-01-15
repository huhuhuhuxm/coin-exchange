package com.exchange.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exchange.constant.LoginConstant;
import com.exchange.entity.SysPrivilege;
import com.exchange.entity.SysUser;
import com.exchange.entity.User;
import com.exchange.mapper.SysPrivilegeMapper;
import com.exchange.mapper.SysUserMapper;
import com.exchange.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Set;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 16:19
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserDetailsService implements UserDetailsService {
    SysUserMapper sysUserMapper;
    SysPrivilegeMapper sysPrivilegeMapper;
    UserMapper userMapper;

    /**
     * 根据用户名查询用户，如果没有查询到用户就会抛出异常 UsernameNotFoundException 【用户名不存在】
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter(LoginConstant.LOGIN_TYPE); //从请求参数中获取登录类型

        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            // 管理员UserDetails生成
            return this.getAdminUserDetails(username);
        } else if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
            //普通UserDetails生成
            return this.getMemberUserDetails(username);
        }
        // 其余类型报错
        throw new IllegalArgumentException("登录类型错误！！！");
    }

    /**
     * 获取管理员UserDetails
     */
    private UserDetails getAdminUserDetails(String username) {
        log.info("=================运行到loadUserByUsername==========");
        // 查询出用户和角色
        SysUser sysUser  = sysUserMapper.selectUserByUsername(username);
        log.info("======================== 用户和角色：{}", sysUser.toString());
        // 从roleSet中过滤出roleId然后弄成集合
        List<Long> roleIds = sysUser.getRoleSet().stream().map(role -> role.getId()).toList();
        log.info("roleIds：{}", roleIds);
        // 查询根据角色再去查询权限
        Set<String> RoleSet = sysPrivilegeMapper.selectPrivilegeByRoleId(roleIds);
        sysUser.setPermsSet(RoleSet);
        log.info("======================== 用户和角色和权限：{}", sysUser);
        return sysUser;
    }


    /**
     * 获取普通用户UserDetails
     */
    private UserDetails getMemberUserDetails(String username) {
        log.info("=================运行到loadUserByUsername==========");
        // 查询出用户
        UserDetails user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
        log.info("查询出用户：{}",user);
        return user;
    }

}
