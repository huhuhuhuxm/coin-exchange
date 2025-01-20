package com.exchange.config.security;

import com.exchange.config.evaluator.RemotePermissionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 23:32
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private RemotePermissionEvaluator remotePermissionEvaluator;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 允许所有请求
                )
                .csrf(csrf -> csrf.disable()); // 禁用 CSRF（可选）
        return http.build();
    }

    /**
     * 配置自定义的Permission的实现
     * @return
     */
    @Bean
    public DefaultMethodSecurityExpressionHandler expressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(remotePermissionEvaluator); // 注入自定义的权限处理器
        return expressionHandler;
    }

}
