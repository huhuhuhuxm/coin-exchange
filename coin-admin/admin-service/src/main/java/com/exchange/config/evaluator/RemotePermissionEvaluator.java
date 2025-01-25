package com.exchange.config.evaluator;

import com.exchange.constants.RoleConstant;
import com.exchange.constants.SysConstants;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.model.R;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 创建自定义权限校验器
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/16 22:29
 */
@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RemotePermissionEvaluator implements PermissionEvaluator {

    AuthorizationServiceFeign authorizationServiceFeign;
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 使用 RequestContextHolder 获取当前请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(SysConstants.AUTHORIZATION);
        // 通过远程调用获取Token中的角色
        R rolesFromTokenResult = authorizationServiceFeign.getRolesFromToken(token);
        // 判断远程调用是否成功 如果内容是异常 则在抛出异常
        if (!ResultCodeEnum.SUCCESS.getCode().equals(rolesFromTokenResult.getCode())) {
            throw new BusinessException(rolesFromTokenResult.getCode(), rolesFromTokenResult.getMsg());
        }
        List<String> roleList = ((List<String>) rolesFromTokenResult.getData()); // TODO 后续将这个缓存到redis中去
        // 如果该Token解析出来的角色是admin管理员 则直接放行通过
        if (!roleList.contains(RoleConstant.ADMIN)) {
            //把permission当做角色来用
            String roles = permission.toString();
            //判断该资源是否有多个角色都可以访问  有分隔符","就表示有多个角色
            boolean isAccess= Arrays.stream(roles.split(",")).anyMatch(roleList::contains);
            if (!isAccess) {
                throw new BusinessException(ResultCodeEnum.PERMISSION.getCode(), ResultCodeEnum.PERMISSION.getMessage());
            }
        }
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
