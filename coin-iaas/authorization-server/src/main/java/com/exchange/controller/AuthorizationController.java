package com.exchange.controller;

import com.exchange.constants.LoginConstant;
import com.exchange.dto.UserLoginDTO;
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
    public ResponseEntity<AuthTokenVO> login(@RequestBody UserLoginDTO userLoginDTO, @RequestParam("login_type") String LoginType) {
        AuthTokenVO authTokenVO = null;
        if (LoginConstant.ADMIN_TYPE.equals(LoginType)) {
            authTokenVO =  sysUserService.getAccessToken(userLoginDTO);
        } else if (LoginConstant.MEMBER_TYPE.equals(LoginType)) {
            authTokenVO = userService.getAccessToken(userLoginDTO);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.error("Authorities：{}", authentication.getAuthorities());
        log.error("\u001B[31mAuthorities：{}\u001B[0m", authentication.getAuthorities());
        log.error("Credentials：{}", authentication.getCredentials());
        log.error("Details：{}", authentication.getDetails());
        log.error("Principal：{}", authentication.getPrincipal());
        // TODO 后续换成通用结果类
        return ResponseEntity.ok(authTokenVO);
    }

    // TODO 在重新获取token的时候把上一个请求的token存入redis黑名单


    /**
     * token解码
     * @param token
     * @return
     */
    @GetMapping("/decodeToken")
    public ResponseEntity<Jwt> decodeToken(String token) {
        Jwt jwt = jwtUtil.decodeToken(token);
        return ResponseEntity.ok(jwt);
    }

    /**
     * 从 Token 中获取用户名
     * @return username
     */
    @GetMapping("/getUsernameFromToken")
    public ResponseEntity<String> getUsernameFromToken(String token) {
        String username = jwtUtil.getUsernameFromToken(token);
        return ResponseEntity.ok(username);
    }

    /**
     * 从Token中获取角色列表
     * @param token
     * @return 角色列表
     */
    @GetMapping("/getRolesFromToken")
    public ResponseEntity<List<String>> getRolesFromToken(String token) {
        List<String> roles = jwtUtil.getRolesFromToken(token);
        return ResponseEntity.ok(roles);
    }


    /**
     * 从Token中获取权限列表
     * @param token
     * @return
     */
    @GetMapping("/getPermissionsFromToken")
    public ResponseEntity<List<String>> getPermissionsFromToken(String token) {
        List<String> permissions = jwtUtil.getPermissionsFromToken(token);
        return ResponseEntity.ok(permissions);
    }


    /**
     * 从Token中获取用户Id
     * @param token
     * @return 用户id
     */
    @GetMapping("/getUserIdFromToken")
    public ResponseEntity<Long> getUserIdFromToken(String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok(userId);
    }


    /**
     * 判断 Token 是否过期
     * @param token
     * @return
     */
    @GetMapping("isTokenExpired")
    public ResponseEntity<Boolean> isTokenExpired(String token) {
        Boolean tokenExpired = jwtUtil.isTokenExpired(token);
        return ResponseEntity.ok(tokenExpired);
    }

}
