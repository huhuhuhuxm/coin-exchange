package com.exchange.vo;

import com.exchange.entity.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * 登录返回值VO
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 13:05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginResultVO {

    private String token;

    private List<SysMenu> menus;

    private List<SimpleGrantedAuthority> authorities;
}
