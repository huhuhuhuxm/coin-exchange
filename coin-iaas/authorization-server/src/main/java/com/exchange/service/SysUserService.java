package com.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exchange.dto.UserLoginDTO;
import com.exchange.entity.SysUser;
import com.exchange.vo.AuthTokenVO;
import org.springframework.stereotype.Service;

/**
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 15:36
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取访问token
     * @param userLoginDTO
     * @return
     */
    AuthTokenVO getAccessToken(UserLoginDTO userLoginDTO);
}
