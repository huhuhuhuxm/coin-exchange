package com.exchange.service.impl;

import com.exchange.constants.LoginConstant;
import com.exchange.dto.UserLoginDTO;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.service.SysLoginService;
import com.exchange.vo.AuthTokenVO;
import com.exchange.vo.LoginResultVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 登录接口实现
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 15:39
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLoginServiceImpl implements SysLoginService {
    AuthorizationServiceFeign authorizationServiceFeign;


    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public LoginResultVO login(String username, String password) {
        log.info("用户开始登录：username:{}", username);
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
        userLoginDTO.setPassword(password);
        ResponseEntity<AuthTokenVO> authTokenResult = authorizationServiceFeign.login(userLoginDTO, LoginConstant.ADMIN_TYPE);
        // 判断远程调用是否成功
        if (authTokenResult.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ResultCodeEnum.FEIGN_FAIL.getCode(), ResultCodeEnum.FEIGN_FAIL.getMessage());
        }

        AuthTokenVO body = authTokenResult.getBody();
        // 封装LoginResultVO
        LoginResultVO loginResultVO = new LoginResultVO();
        String accessToken = body.getAccessToken();
        loginResultVO.setToken(accessToken);
//        loginResultVO.setMenus();
//        loginResultVO.setAuthorities();


        return null;
    }

}
