package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.constants.JwtConstant;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.SysUser;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.mapper.SysUserMapper;
import com.exchange.service.SysUserService;
import com.exchange.utils.JwtUtil;
import com.exchange.vo.AuthTokenVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
            throw new BusinessException(ResultCodeEnum.ACCOUNT_PASSWORD_ERROR.getCode(), ResultCodeEnum.ACCOUNT_PASSWORD_ERROR.getMessage());
        }
        SysUser user = (SysUser) authenticate.getPrincipal();
        log.info("登陆后的用户=》》》{}", user);
        // 将登录成功的用户存入SecurityContext中
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        // 封装claims
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.JWT_USER_ID, user.getId());
        claims.put(JwtConstant.JWt_USERNAME, user.getUsername());
        claims.put(JwtConstant.JWT_ROLE, user.getRoleSet().stream().map(role -> role.getCode()).toList());
        claims.put(JwtConstant.JWT_PERMISSION, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

        // 生成token
        Map<String, Object> generateToken = jwtUtil.generateToken(user.getUsername(), claims);

        log.info("accessToken：{}", generateToken.get("accessToken"));
        log.info("refreshToken：{}", generateToken.get("refreshToken"));
        // TODO 目前先返回一个accessToken
        AuthTokenVO authTokenVO = new AuthTokenVO();
        authTokenVO.setAccessToken(generateToken.get("accessToken").toString());
        authTokenVO.setRefreshToken(generateToken.get("refreshToken").toString());

        return authTokenVO;
    }
}
