package com.qingshuihe.common.infrastructure.exception;

/**
 * @Description:该枚举类定义与业务相关的异常
 * @Author: shl
 * @Date: 2023/1/28
 **/
public enum AppexcepitonCodeMsg {
    SYSTEM_ERROR(500,"系统异常"),
    REDIS_CONNECTION_ERROR(30001,"服务器无响应，请等待后重试"),
    INVAILD_PARAMETER(10000,"无效参数"),

    //登陆相关返回信息
    VERIFY_CODE_OUTDATE(10001,"验证码已过期"),
    WRONG_PASSWORD(10003,"用户名或密码错误"),
    LOGOUT_FAILED(10004,"登出失败");
    ;

    private int code;
    private String message;

    AppexcepitonCodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode(){
        return code;
    }
    public String getMessage(){
        return message;
    }
}
