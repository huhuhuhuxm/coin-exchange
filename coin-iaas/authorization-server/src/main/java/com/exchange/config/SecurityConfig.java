package com.exchange.config;

import com.exchange.service.SysUserDetailsService;
import com.exchange.service.SysUserService;
import com.exchange.service.impl.SysUserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SpringSecurity配置
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/10 16:44
 */
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityConfig {

     SysUserDetailsService sysUserDetailsService;

    /**
     * 用于认证的Spring Security过滤器链。
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**")// 允许登录接口直接放问
                .permitAll()
                .anyRequest() // 其余请求需要认证
                .authenticated())
                .csrf(csrf -> csrf.disable()) // 禁用csrf
//                .cors(Customizer.withDefaults()) // 启用跨域
                .cors(cors -> cors.disable()) // 禁用跨域
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())) // 配置为资源服务器， 解析JWT token
                .formLogin(AbstractHttpConfigurer::disable) // 禁用默认表单登录
                .httpBasic(AbstractHttpConfigurer::disable); // 禁用Basic认证
        return http.build();
    }

    /**
     * AuthenticationManger：负责认证
     * DaoAuthenticationProvider：负责将sysUserDetailsService, passwordEncoder融合到AuthenticationManger中
     * @param passwordEncoder
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(sysUserDetailsService);
        // 关联使用的密码编码器
        provider.setPasswordEncoder(passwordEncoder);
        // 讲provider放置到AuthenticationManager中
        ProviderManager providerManager = new ProviderManager(provider);
        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

}
