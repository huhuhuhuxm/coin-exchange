package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.User;
import com.exchange.mapper.UserMapper;
import com.exchange.service.UserService;
import com.exchange.utils.JwtUtil;
import com.exchange.vo.AuthTokenVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/15 20:41
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    AuthenticationManager authenticationManager;
    JwtUtil jwtUtil;


    /**
     * 登录并获取token
     * @param userLoginDTO
     * @return
     */
    @Override
    public AuthTokenVO getAccessToken(UserLoginDTO userLoginDTO) {
        // 传入用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        // 实现登录逻辑,此时回去调用loadUserByUsername
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("用户名或密码错误：{}", e.fillInStackTrace());
            throw new AuthenticationServiceException("用户名或密码错误", e);
        }
        User user = (User) authenticate.getPrincipal();
        log.info("登录后的普通用户：{}", user);

        // 获取token
        Map<String, Object> generateToken = jwtUtil.generateToken(user.getUsername(), new HashMap<>());

        log.info("accessToken：{}", generateToken.get("accessToken"));
        log.info("refreshToken：{}", generateToken.get("refreshToken"));
        // TODO 目前先返回一个accessToken
        AuthTokenVO authTokenVO = new AuthTokenVO();
        authTokenVO.setAccessToken(generateToken.get("accessToken").toString());
        authTokenVO.setRefreshToken(generateToken.get("refreshToken").toString());

        return authTokenVO;
    }


    /**
     * 角色校验
     * @param role
     * @param token
     * @return
     */
    @Override
    public boolean roleCheck(String role, String token) {
        // TODO 根据token解析出角色
        return false;
    }
}
