package com.exchange.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 19:46
 */
@RestController
@RequestMapping("/test")
public class AuthTestController {

    @PreAuthorize("hasPermission('admin')")
    @GetMapping("/auth")
    public String testAuth() {
        return "测试成功！！！！";
    }

    @PreAuthorize("hasPermission(#target, 'read')")
    @GetMapping("/test1")
    public String someMethod(String target) {
        return "Some data";
    }

}
