package com.exchange.interceptor;

import com.exchange.constants.SysConstants;
import com.exchange.enumeration.ResultCodeEnum;
import com.exchange.exception.BusinessException;
import com.exchange.feign.AuthorizationServiceFeign;
import com.exchange.model.R;
import com.exchange.utils.UserContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用户拦截器
 * @author huxuanming
 * @version 1.0
 * @date 2025/2/5 21:39
 */
@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {
    @Resource
    private AuthorizationServiceFeign authorizationServiceFeign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取Token并存储到TheadLocal中去
        String token = request.getHeader(SysConstants.AUTHORIZATION);
        // 通过远程调用获取Token中的角色
        R rolesFromTokenResult = authorizationServiceFeign.getUserIdFromToken(token);
        // 判断远程调用是否成功 如果内容是异常 则在抛出异常
        if (!ResultCodeEnum.SUCCESS.getCode().equals(rolesFromTokenResult.getCode())) {
            throw new BusinessException(rolesFromTokenResult.getCode(), rolesFromTokenResult.getMsg());
        }
        Long userId = Long.valueOf(rolesFromTokenResult.getData().toString());
        UserContextUtil.setUserID(userId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 确保请求处理结束后清理 ThreadLocal 数据，防止线程污染
        UserContextUtil.clear();
    }
}
