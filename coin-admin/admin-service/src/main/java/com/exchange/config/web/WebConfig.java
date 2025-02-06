package com.exchange.config.web;

import com.exchange.interceptor.UserInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/6 19:24
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加 UserInterceptor，并指定拦截路径（/** 表示拦截所有请求）
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns("/login"); // 排除登录、注册等无需 Token 的接口
    }
}
