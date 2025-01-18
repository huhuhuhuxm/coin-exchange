package com.exchange.exception;

/**
 * 业务异常
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/18 21:13
 */
public class BusinessException extends RuntimeException{

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
