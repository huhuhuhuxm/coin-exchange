package com.exchange.feign;

import com.exchange.dto.UserLoginDTO;
import com.exchange.vo.AuthTokenVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 14:49
 */
@FeignClient(name = "member-service")
public interface AuthorizationServiceFeign {

    /**
     * 用户登录
     * @param userLoginDTO
     * @param LoginType
     * @return
     */
    @PostMapping("/auth/login")
    ResponseEntity<AuthTokenVO> login(@RequestBody UserLoginDTO userLoginDTO, @RequestParam("login_type") String LoginType);

}
