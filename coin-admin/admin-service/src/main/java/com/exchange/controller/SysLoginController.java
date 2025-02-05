package com.exchange.controller;

import com.exchange.constants.LoginConstant;
import com.exchange.dto.UserLoginDTO;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.service.SysLoginService;
import com.exchange.vo.AuthTokenVO;
import com.exchange.vo.LoginResultVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 登录相关接口
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 13:09
 */
@Slf4j
@RestController
@RequestMapping("/")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginController {
    SysLoginService sysLoginService;

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @Operation(description = "登录")
    @PostMapping("login")
    public LoginResultVO login(String username, String password) {
        return sysLoginService.login(username, password);
    }

}
