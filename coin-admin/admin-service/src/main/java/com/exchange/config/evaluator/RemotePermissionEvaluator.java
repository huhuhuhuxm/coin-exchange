package com.exchange.config.evaluator;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        // TODO 后续主要权限匹配需要远程调用authorization-server
        // 从 SecurityContextHolder 获取当前用户的身份信息
        String username = authentication.getName();
        // 使用 RequestContextHolder 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        log.info("username: {}", username);
        log.info("token: {}", token);
        log.info("Authority：{}", authentication.getAuthorities());
        log.info("permission：{}", permission);

        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
