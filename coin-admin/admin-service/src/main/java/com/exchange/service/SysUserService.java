package com.exchange.service;

import com.exchange.dto.UserDTO;
import com.exchange.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 添加员工
     * @param userDTO
     * @return
     */
    boolean addUser(UserDTO userDTO);

    /**
     * 编辑员工
     * @param userDTO
     * @return
     */
    boolean updateUser(UserDTO userDTO);

    /**
     * 批量删除员工
     * @param userIds
     * @return
     */
    boolean deleteUsers(List<Long> userIds);
}
