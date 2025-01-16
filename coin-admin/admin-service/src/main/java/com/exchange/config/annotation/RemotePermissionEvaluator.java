package com.exchange.config.annotation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 创建自定义权限校验器
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 22:29
 */
@Component
@Slf4j
public class RemotePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 从 SecurityContextHolder 获取当前用户的身份信息
        String username = authentication.getName();
        String token = (String) authentication.getCredentials();
        log.info("username: {}", username);
        log.info("token: {}", token);
        System.out.println(targetDomainObject);
        System.out.println(permission);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
