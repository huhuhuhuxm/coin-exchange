package com.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exchange.constants.SysConstants;
import com.exchange.entity.SysUser;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.model.R;
import com.exchange.service.SysUserService;
import com.exchange.mapper.SysUserMapper;
import com.mysql.cj.log.Log;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
* @author hxm
* @description 针对表【sys_user(平台用户)】的数据库操作Service实现
* @createDate 2025-01-19 12:41:08
*/
@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    AuthorizationServiceFeign authorizationServiceFeign;

    @Override
    public Long getUserId() {
        // 使用 RequestContextHolder 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(SysConstants.AUTHORIZATION);
        // 远程调用获取userId
        R userIdFromToken = authorizationServiceFeign.getUserIdFromToken(token);
        // 判断远程调用是否成功 如果内容是异常 则在抛出异常
        if (!ResultCodeEnum.SUCCESS.getCode().equals(userIdFromToken.getCode())) {
            throw new BusinessException(userIdFromToken.getCode(), userIdFromToken.getMsg());
        }
        Long userId = Long.valueOf(userIdFromToken.getData().toString());
        return userId;
    }
}




