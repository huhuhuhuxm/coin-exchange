package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.SysUser;
import com.exchange.mapper.SysUserMapper;
import com.exchange.service.SysUserService;
import com.exchange.utils.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 15:38
 */
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    AuthenticationManager authenticationManager;
    JwtUtil jwtUtil;

    /**
     * 获取访问token
     * @param userLoginDTO
     * @return
     */
    @Override
    public String getAccessToken(UserLoginDTO userLoginDTO) {
        // 传入用户名和密码
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        // 实现登录逻辑,此时回去调用loadUserByUsername
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("用户名或密码错误：{}", e.fillInStackTrace());
            return "用户名或密码错误！！！";
        }
        SysUser user = (SysUser) authenticate.getPrincipal();
        log.info("登陆后的用户=》》》{}", user);
        Map<String, Object> generateToken = jwtUtil.generateToken(user);
        log.info("accessToken：{}", generateToken.get("accessToken"));
        log.info("refreshToken：{}", generateToken.get("refreshToken"));
        // TODO 目前先返回一个accessToken
        return generateToken.get("accessToken").toString();
    }
}
