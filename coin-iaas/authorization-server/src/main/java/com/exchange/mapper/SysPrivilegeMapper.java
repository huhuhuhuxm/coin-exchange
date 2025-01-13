package com.exchange.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.exchange.entity.SysPrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * SysPrivilege
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/12 20:56
 */
@Mapper
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

    /**
     * 查询根据角色Id查询权限集合
     * @param roleIds
     * @return
     */
    Set<String> selectPrivilegeByRoleId(List<Long> roleIds);
}
