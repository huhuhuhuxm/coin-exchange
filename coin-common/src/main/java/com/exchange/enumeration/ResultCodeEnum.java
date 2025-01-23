package com.exchange.enumeration;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 * @author huxuanming
 * @version 1.0
 * @date 2025/1/18 21:57
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),
    FEIGN_FAIL(207, "远程调用失败"),
    UPDATE_ERROR(204, "数据更新失败"),

    ARGUMENT_VALID_ERROR(210, "参数校验异常"),
    SIGN_ERROR(300, "签名错误"),
    SIGN_OVERDUE(301, "签名已过期"),
    VALIDATECODE_ERROR(218 , "验证码错误"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),
    ACCOUNT_ERROR(214, "账号不正确"),
    PASSWORD_ERROR(215, "密码不正确"),
    PHONE_CODE_ERROR(215, "手机验证码不正确"),
    LOGIN_MOBLE_ERROR( 216, "账号不正确"),
    ACCOUNT_PASSWORD_ERROR(217, "账号或密码不正确")
    ;

    private Integer code;

    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
