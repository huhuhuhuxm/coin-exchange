package com.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户表
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 10:25
 */
@Slf4j
@Data
@TableName("sys_user")
public class SysUser implements UserDetails, Serializable {

    @TableId
    private Long id; // id主键
    private String username; // 账号
    private String password; // 密码
    private String fullname; // 姓名
    private String mobile; // 手机号
    private String email; // 邮箱
    @TableLogic(value = "1", delval = "0")
    private Integer status; // 状态 0-无效； 1-有效
    private Long createBy; // 创建人
    private Long modifyBy; // 修改人
    private LocalDateTime created; // 创建时间
    private LocalDateTime lastUpdateTime; // 修改时间

    // 角色信息
    @TableField(exist = false) // 该字段不会参与查询
    private Set<SysRole> roleSet = new HashSet<>();
    //权限信息
    @TableField(exist = false) // 该字段不会参与查询
    private Set<String> permsSet = new HashSet<>();

    // 得到该账户权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 将权限告知SpringSecurity，通过lambda表达式将Set<String>转成Collection<GrantedAuthority>
        if (permsSet != null && !permsSet.isEmpty()) {
            log.info("permsSet转换后：{}", permsSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
            return permsSet.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        }
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status == 1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status == 1;
    }

    @Override
    public boolean isEnabled() {
        return this.status == 1;
    }
}
