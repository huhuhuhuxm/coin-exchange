package com.exchange.service;

import com.exchange.vo.LoginResultVO;

/**
 * 登录接口
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/20 15:17
 */
public interface SysLoginService {

    /**
     * 管理员登录
     * @param username
     * @param password
     * @return
     */
    LoginResultVO login(String username, String password);

}
