package com.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exchange.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * SysUserMapper
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 15:20
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser selectUserByUsername(String username);
}
