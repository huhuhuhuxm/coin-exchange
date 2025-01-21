package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.SysRole;
import com.exchange.service.SysRoleService;
import com.exchange.mapper.SysRoleMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author hxm
* @description 针对表【sys_role(角色)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
    implements SysRoleService{

    SysRoleMapper sysRoleMapper;

    /**
     * 根据userId查询出roleCode
     * @param userId
     * @return
     */
    @Override
    public boolean isAdmin(Long userId) {
        String roleCode = sysRoleMapper.selectUserRoleCodeByUserId(userId);
        return "ROLE_ADMIN".equals(roleCode);
    }
}




