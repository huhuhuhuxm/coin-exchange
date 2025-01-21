package com.exchange.service;

import com.exchange.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author hxm
* @description 针对表【sys_menu(系统菜单)】的数据库操作Service
* @createDate 2025-01-19 12:41:08
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 通过userId查询菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> getMenusByUserId(Long userId);
}
