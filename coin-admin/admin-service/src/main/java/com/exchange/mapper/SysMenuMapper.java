package com.exchange.mapper;

import com.exchange.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hxm
* @description 针对表【sys_menu(系统菜单)】的数据库操作Mapper
* @createDate 2025-01-19 12:41:08
* @Entity com.exchange.entity.SysMenu
*/
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据userId查询出菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> selectMenusByUserId(Long userId);
}




