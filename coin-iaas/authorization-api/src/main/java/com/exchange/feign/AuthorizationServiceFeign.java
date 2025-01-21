package com.exchange.feign;

import com.exchange.dto.UserLoginDTO;
import com.exchange.vo.AuthTokenVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 14:49
 */
@FeignClient(name = "authorization-server")
public interface AuthorizationServiceFeign {

    /**
     * 用户登录
     * @param userLoginDTO
     * @param LoginType
     * @return
     */
    @PostMapping("/auth/login")
    ResponseEntity<Object> login(@RequestBody UserLoginDTO userLoginDTO, @RequestParam("login_type") String LoginType);

    /**
     * 从Token中获取权限列表
     * @param token
     * @return
     */
    @GetMapping("/auth/getPermissionsFromToken")
    ResponseEntity<List<String>> getPermissionsFromToken(@RequestParam String token);

    /**
     * 从Token中获取用户Id
     * @param token
     * @return 用户id
     */
    @GetMapping("/auth/getUserIdFromToken")
    ResponseEntity<Long> getUserIdFromToken(@RequestParam String token);

}
