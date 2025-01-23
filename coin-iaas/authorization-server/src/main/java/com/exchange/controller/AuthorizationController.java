package com.exchange.controller;

import com.exchange.constants.LoginConstant;
import com.exchange.dto.UserLoginDTO;
import com.exchange.model.R;
import com.exchange.service.SysUserService;
import com.exchange.service.UserService;
import com.exchange.utils.JwtUtil;
import com.exchange.vo.AuthTokenVO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    UserService userService;
    JwtUtil jwtUtil;

    /**
     * 用户登录
     * @param userLoginDTO
     * @param LoginType
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UserLoginDTO userLoginDTO, @RequestParam("login_type") String LoginType) {
        AuthTokenVO authTokenVO = null;
        if (LoginConstant.ADMIN_TYPE.equals(LoginType)) {
            authTokenVO =  sysUserService.getAccessToken(userLoginDTO);
        } else if (LoginConstant.MEMBER_TYPE.equals(LoginType)) {
            authTokenVO = userService.getAccessToken(userLoginDTO);
        }
        return R.ok(authTokenVO);
    }

    // TODO 在重新获取token的时候把上一个请求的token存入redis黑名单


    /**
     * token解码
     * @param token
     * @return
     */
    @GetMapping("/decodeToken")
    public R decodeToken(@RequestParam String token) {
        Jwt jwt = jwtUtil.decodeToken(token);
        return R.ok(jwt);
    }

    /**
     * 从 Token 中获取用户名
     * @return username
     */
    @GetMapping("/getUsernameFromToken")
    public R getUsernameFromToken(@RequestParam String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return R.ok(username);
    }

    /**
     * 从Token中获取角色列表
     * @param token
     * @return 角色列表
     */
    @GetMapping("/getRolesFromToken")
    public R getRolesFromToken(@RequestParam String token) {
        List<String> roles = jwtUtil.getRolesFromToken(token);
        return R.ok(roles);
    }


    /**
     * 从Token中获取权限列表
     * @param token
     * @return
     */
    @GetMapping("/getPermissionsFromToken")
    public R getPermissionsFromToken(@RequestParam String token) {
        List<String> permissions = jwtUtil.getPermissionsFromToken(token);
        return R.ok(permissions);
    }


    /**
     * 从Token中获取用户Id
     * @param token
     * @return 用户id
     */
    @GetMapping("/getUserIdFromToken")
    public R getUserIdFromToken(@RequestParam String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return R.ok(userId);
    }


    /**
     * 判断 Token 是否过期
     * @param token
     * @return
     */
    @GetMapping("isTokenExpired")
    public R isTokenExpired(@RequestParam String token) {
        Boolean tokenExpired = jwtUtil.isTokenExpired(token);
        return R.ok(tokenExpired);
    }

}
