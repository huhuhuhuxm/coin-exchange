package com.exchange.controller;

import com.exchange.entity.SysUser;
import com.exchange.mapper.ConfigMapper;
import com.exchange.mapper.SysUserMapper;
import com.exchange.model.R;
import com.exchange.service.SysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 19:46
 */
@RestController
@RequestMapping("/test")
public class AuthTestController {

    @Autowired
    SysUserService sysUserService;

    @PreAuthorize("hasAnyAuthority('admin', 'admin1', 'admin2')")
    @GetMapping("/auth")
    public String testAuth() {
        return "测试成功！！！！";
    }


    @PreAuthorize("has('admin')")
    @GetMapping("/auth1")
    public String testAuth1() {
        return "测试成功！！！！";
    }

    @PreAuthorize("hasPermission(null, 'ROLE_ADMIN,user')")
    @GetMapping("/test1")
    public String someMethod(String target) {
        return "Some data";
    }


    @GetMapping("/user")
    public R getUser() {
        SysUser sysUser = sysUserService.getById(1010101010101010101L);
        return R.ok(sysUser);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/mapper")
    public String testMapper() {
        return "Jackson Modules: " + objectMapper.getRegisteredModuleIds();
    }

    @Autowired
    List<HttpMessageConverter<?>> converters;

    @GetMapping("/converters")
    public String testConverters() {
        return "Converters: " + converters;
    }

}
