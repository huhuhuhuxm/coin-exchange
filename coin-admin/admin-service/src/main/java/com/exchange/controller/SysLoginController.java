package com.exchange.controller;

import com.exchange.vo.LoginResultVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public LoginResultVO login(String username, String password) {

        return null;
    }

}
