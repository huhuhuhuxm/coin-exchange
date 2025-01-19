package com.exchange.aspect;

import com.alibaba.fastjson2.JSON;
import com.exchange.model.WebLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
@Order(1)
@Slf4j
public class WebLogAspect {

    @Around("execution(* com.exchange.controller.*.*(..))")
    public Object recodeWebLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        WebLog webLog = new WebLog();
        long start = System.currentTimeMillis();
        // 执行方法的真实调用
        result = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long end = System.currentTimeMillis();

        webLog.setSpendTime((int) ((start - end)/1000)); // 请求接口花费的时间
        // 获取当前请求的request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取安全上下文
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String url = request.getRequestURL().toString();
        String baseURL = request.getScheme() + "://" + request.getServerName() +
                ((request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : ":" + request.getServerPort());
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(url);
        webLog.setBasePath(baseURL);
        webLog.setUsername(authentication == null ? "anonymous" : authentication.getPrincipal().toString());
        webLog.setIp(request.getRemoteAddr()); // TODO 获取ip地址

        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取方法上的ApiOperation注解
        Operation annotation = method.getAnnotation(Operation.class);
        // 获取目标对象的类型名称
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        webLog.setDescription(annotation == null ? "no desc": annotation.description());
        webLog.setMethod(className + "." + method.getName());
        webLog.setParameter(getMethodParameter(method, proceedingJoinPoint.getArgs()));
        webLog.setResult(result);
        log.info(JSON.toJSONString(webLog));
        return result;
    }

    /**
     * 获取方法执行参数
     * @param method
     * @return
     */
    private Object getMethodParameter(Method method, Object[] args) throws JsonProcessingException {
        Map<String, Object> methodParametersWithValues  = new HashMap<>();
        StandardReflectionParameterNameDiscoverer standardReflectionParameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();

        // 方法形参名称
        String[] parameterNames = standardReflectionParameterNameDiscoverer.getParameterNames(method);

        for (int i = 0; i < parameterNames.length; i++) {
            methodParametersWithValues.put(parameterNames[i], args[i]);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.writeValueAsString(methodParametersWithValues);
    }
}
