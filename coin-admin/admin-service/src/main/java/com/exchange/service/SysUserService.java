package com.exchange.service;

import com.exchange.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author hxm
* @description 针对表【sys_user(平台用户)】的数据库操作Service
* @createDate 2025-01-19 12:41:08
*/
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取用户id
     * @return
     */
    Long getUserId();

}
