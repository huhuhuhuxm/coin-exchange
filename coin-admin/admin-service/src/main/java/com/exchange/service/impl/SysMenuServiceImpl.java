package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.SysMenu;
import com.exchange.entity.SysUser;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.service.SysMenuService;
import com.exchange.mapper.SysMenuMapper;
import com.exchange.service.SysRoleService;
import com.exchange.service.SysUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author hxm
* @description 针对表【sys_menu(系统菜单)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:07
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    SysRoleService sysRoleService;
    SysMenuMapper sysMenuMapper;

    /**
     * 根据userId查询菜单列表
     * @param userId
     * @return
     */
    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        // 根据userId 查询用户角色
        boolean isAdmin = sysRoleService.isAdmin(userId);
        if (isAdmin) {
            // 如果用户是管理员则拥有所有菜单
            return sysMenuMapper.selectList(null);
        } else {
            // 如果不是管理员则根据userid查询出角色id 更具角色id查询出菜单id 然后根据菜单id查询出菜单
            List<SysMenu> menus = sysMenuMapper.selectMenusByUserId(userId);
            return menus;
        }
    }
}




