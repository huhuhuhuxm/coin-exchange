package com.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.entity.SysMenu;
import com.exchange.entity.SysPrivilege;
import com.exchange.entity.SysRolePrivilege;
import com.exchange.service.SysMenuService;
import com.exchange.service.SysPrivilegeService;
import com.exchange.service.SysRolePrivilegeService;
import com.exchange.mapper.SysRolePrivilegeMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author hxm
* @description 针对表【sys_role_privilege(角色权限配置)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege>
    implements SysRolePrivilegeService{

    SysMenuService sysMenuService;
    SysPrivilegeService sysPrivilegeService;
    SysRolePrivilegeMapper sysRolePrivilegeMapper;




    /**
     * 根据角色id查询菜单和权限
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuAndPrivileges(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.list(); //查询所有菜单
        // 在页面显示的是二级菜单，以及二级菜单包含的权限
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return Collections.emptyList();
        }
        //过滤出一级菜单
        List<SysMenu> rootMenus = sysMenuList.stream().filter(sysMenu -> sysMenu.getParentId() == null).toList();
        log.info("rootMenus：{}", rootMenus);
        if (CollectionUtils.isEmpty(rootMenus)) {
            return Collections.emptyList();
        }

        // 过滤出二级菜单列表
        List<SysMenu> secondaryMenus = sysMenuList.stream()
                .filter(sysMenu -> StringUtils.countOccurrencesOf(sysMenu.getParentKey(), ".") == 0 && sysMenu.getParentKey() != null)
                .toList();
        log.info("secondaryMenus：{}", secondaryMenus);
        if (CollectionUtils.isEmpty(secondaryMenus)) {
            return Collections.emptyList();
        }

        // 查询该角色所对应的权限id集合
        List<Long> sysPrivilegeIds = sysRolePrivilegeMapper.selectList(
                new LambdaQueryWrapper<SysRolePrivilege>().eq(SysRolePrivilege::getRoleId, roleId))
                .stream()
                .map(sysRolePrivilege -> sysRolePrivilege.getPrivilegeId())
                .toList();

        // 查询出所有权限并且标记角色拥有对应权限
        List<SysPrivilege> sysPrivileges = sysPrivilegeService.getAllPrivilege(sysPrivilegeIds);

        // 把权限塞入对应二级菜单中
        for (SysMenu secondaryMenu : secondaryMenus) {
            List<SysPrivilege> list = sysPrivileges.stream()
                    .filter(sysPrivilege -> secondaryMenu.getId().equals(sysPrivilege.getMenuId()))
                    .toList();
            secondaryMenu.setPrivileges(list);
        }


        // ---------------------------------------------

        // 查询所有二级菜单
//        List<SysMenu> subMenus = new ArrayList<>();
//        for (SysMenu rootMenu : rootMenus) {
//            subMenus.addAll(this.getChildMenus(rootMenu.getId(), roleId, sysMenuList, sysPrivileges));
//        }
//

        // ---------------------------------------------

        return secondaryMenus;

    }

    /**
     * 查询菜单的子菜单
     * @param parentId 父菜单Id
     * @param roleId 角色Id
     * @return
     */
//    private List<SysMenu> getChildMenus(Long parentId, Long roleId, List<SysMenu> sysMenuList, List<SysPrivilege> sysPrivileges) {
//        List<SysMenu> childList = new ArrayList<>();
//        for (SysMenu sysMenu : sysMenuList) {
//            if (sysMenu.getParentId().equals(parentId)) {
//                childList.add(sysMenu);
//                sysMenu.setChilds(this.getChildMenus(sysMenu.getId(), roleId, sysMenuList, sysPrivileges));
//                sysMenu.setPrivileges();
//            }
//        }
//        return null;
//    }
}




