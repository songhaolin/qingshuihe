package com.qingshuihe.common.infrastructure.exception.handler;

import com.qingshuihe.common.infrastructure.exception.AppexcepitonCodeMsg;
import com.qingshuihe.common.infrastructure.exception.Appexception;
import com.qingshuihe.common.infrastructure.exception.Resp;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:定义异常拦截器，遇到抛出异常的情况会在这里统一处理并返回前端
 * 开发过程中如有问题直接抛出在异常枚举类中定义好的情况即可，无需直接返回或try...catch
 * @Author: shl
 * @Date: 2023/1/28
 **/
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> Resp<T> exceptionHandler(Exception e){
        if (e instanceof Appexception){
            Appexception appexception = (Appexception) e;
            return Resp.error(appexception.getCode(),appexception.getMessage());
        }
        if (e instanceof BadCredentialsException){
            BadCredentialsException badCredentialsException = (BadCredentialsException) e;
            return Resp.error(AppexcepitonCodeMsg.WRONG_PASSWORD.getCode(),badCredentialsException.getMessage());
        }
        return Resp.error(AppexcepitonCodeMsg.SYSTEM_ERROR);
    }
}
