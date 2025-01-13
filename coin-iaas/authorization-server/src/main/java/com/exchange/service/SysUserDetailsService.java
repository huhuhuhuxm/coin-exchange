package com.exchange.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.exchange.entity.SysPrivilege;
import com.exchange.entity.SysUser;
import com.exchange.mapper.SysPrivilegeMapper;
import com.exchange.mapper.SysUserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    /**
     * 根据用户名查询用户，如果没有查询到用户就会抛出异常 UsernameNotFoundException 【用户名不存在】
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
}
