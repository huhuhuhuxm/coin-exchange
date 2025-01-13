package com.exchange.controller;

import com.exchange.dto.UserLoginDTO;
import com.exchange.service.SysUserService;
import com.exchange.vo.AuthTokenVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 授权相关接口
 * @author huxuanming
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthorizationController {

    SysUserService sysUserService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public AuthTokenVO login(@RequestBody UserLoginDTO userLoginDTO) {
        AuthTokenVO authTokenVO =  sysUserService.getAccessToken(userLoginDTO);
//        AuthTokenVO authTokenVO = new AuthTokenVO();
//        authTokenVO.setAccessToken(accessToken);
        // TODO 后续换成通用结果类
        return authTokenVO;
    }

}
