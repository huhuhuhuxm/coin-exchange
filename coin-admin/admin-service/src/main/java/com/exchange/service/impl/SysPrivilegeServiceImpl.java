package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.SysMenu;
import com.exchange.entity.SysPrivilege;
import com.exchange.service.SysPrivilegeService;
import com.exchange.mapper.SysPrivilegeMapper;
import com.exchange.service.SysRolePrivilegeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
* @author hxm
* @description 针对表【sys_privilege(权限配置)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege>
    implements SysPrivilegeService{

    SysPrivilegeMapper sysPrivilegeMapper;

    /**
     * 查询出所有权限并且标记角色拥有对应权限
     * @param sysPrivilegeIds
     * @return
     */
    @Override
    public List<SysPrivilege> getAllPrivilege(List<Long> sysPrivilegeIds) {
        // 查询所有权限
        List<SysPrivilege> sysPrivileges = sysPrivilegeMapper.selectList(null);
        // 过滤出角色拥有的权限并打上并修改own属性
        for (SysPrivilege sysPrivilege : sysPrivileges) {
            if (sysPrivilegeIds.contains(sysPrivilege.getId())) {
                sysPrivilege.setOwn(1);
            }
        }
//        List<SysPrivilege> list = sysPrivileges.stream()
//                .filter(sysPrivilege -> sysPrivilegeIds.contains(sysPrivilege.getId())) // 过滤出该角色拥有的权限
//                .peek(sysPrivilege -> sysPrivilege.setOwn(1)) // 如果拥有该角色则修改Own属性为1
//                .toList();
        return sysPrivileges;
    }
}




