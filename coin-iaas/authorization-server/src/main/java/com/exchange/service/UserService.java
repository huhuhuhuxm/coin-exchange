package com.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.User;
import com.exchange.vo.AuthTokenVO;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/15 20:40
 */
public interface UserService extends IService<User> {

    /**
     * 登录并获取token
     * @param userLoginDTO
     * @return
     */
    AuthTokenVO getAccessToken(UserLoginDTO userLoginDTO);

    /**
     * 角色校验
     * @param role
     * @param token
     * @return
     */
    boolean roleCheck(String role, String token);
}
