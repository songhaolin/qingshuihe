package com.qingshuihe.common.infrastructure.exception;

/**
 * @Description:自定义异常类，用以统一处理全局异常信息
 * @Author: shl
 * @Date: 2023/1/28
 **/
public class Appexception extends RuntimeException{

    private int code = AppexcepitonCodeMsg.SYSTEM_ERROR.getCode();
    private String message = AppexcepitonCodeMsg.SYSTEM_ERROR.getMessage();


    public Appexception(AppexcepitonCodeMsg appexcepitonCodeMsg){
        super();
        this.code = appexcepitonCodeMsg.getCode();
        this.message = appexcepitonCodeMsg.getMessage();
    }
    public Appexception(int code ,String message){
        super();
        this.code = code;
        this.message = message;
    }
    public int getCode(){
        return code;
    }
    @Override
    public String getMessage(){
        return message;
    }

}
