package com.exchange.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @PreAuthorize("hasPermission(null, 'admin,user')")
    @GetMapping("/test1")
    public String someMethod(String target) {
        return "Some data";
    }

}
