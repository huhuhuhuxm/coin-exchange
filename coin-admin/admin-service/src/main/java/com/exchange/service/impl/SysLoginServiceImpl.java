package com.exchange.service.impl;

import com.exchange.constants.LoginConstant;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.SysMenu;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.service.SysLoginService;
import com.exchange.service.SysMenuService;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    SysMenuService sysMenuService;


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
        // 根据Token查询出该用户的权限列表
        ResponseEntity<List<String>> permissionsResult = authorizationServiceFeign.getPermissionsFromToken(accessToken);

        // 判断远程调用是否成功
        if (permissionsResult.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ResultCodeEnum.FEIGN_FAIL.getCode(), ResultCodeEnum.FEIGN_FAIL.getMessage());
        }
        List<String> authorities = permissionsResult.getBody();

        List<SimpleGrantedAuthority> collect = authorities.stream()
                .map(SimpleGrantedAuthority::new) // 将每个 String 转换为 SimpleGrantedAuthority
                .collect(Collectors.toList());

        loginResultVO.setAuthorities(collect);

        // 根据Token解析出用户id
        ResponseEntity<Long> userIdFromTokenResult = authorizationServiceFeign.getUserIdFromToken(accessToken);

        // 判断远程调用是否成功
        if (userIdFromTokenResult.getStatusCode() != HttpStatus.OK) {
            throw new BusinessException(ResultCodeEnum.FEIGN_FAIL.getCode(), ResultCodeEnum.FEIGN_FAIL.getMessage());
        }

        Long userId = userIdFromTokenResult.getBody();

        // 根据userId查询菜单列表
         List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);

        loginResultVO.setMenus(menus);

        return loginResultVO;
    }

}
