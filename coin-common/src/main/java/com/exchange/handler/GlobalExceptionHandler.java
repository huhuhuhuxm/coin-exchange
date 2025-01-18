package com.exchange.handler;

import com.exchange.exception.BusinessException;
import com.exchange.exception.SystemException;
import com.exchange.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常类
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/18 20:48
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public R systemExceptionHandler(SystemException e) {
        log.error("异常信息：{}", e.getMessage());
        if (e.getCode() != null) {
            return R.fail(e.getCode(), e.getMessage());
        }
        return R.fail(e.getMessage());
    }

    /**
     * 业务异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e) {
        log.error("异常信息：{}", e.getMessage());
        if (e.getCode() != null) {
            return R.fail(e.getCode(), e.getMessage());
        }
        return R.fail(e.getMessage());
    }


    /**
     * 处理参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("异常信息: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError!=null) {
                return R.fail(fieldError.getField() + fieldError.getDefaultMessage());
            }
        }
        return R.fail(e.getMessage());
    }


    /**
     * 处理校验异常（表单数据绑定错误）
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public R handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return R.fail(message);
    }


    /**
     * 处理其他未知异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        log.error("异常信息：{}", e.getMessage());
        return R.fail(e.getMessage());
    }

}
