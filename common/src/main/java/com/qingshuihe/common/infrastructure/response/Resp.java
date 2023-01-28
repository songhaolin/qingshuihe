package com.qingshuihe.common.infrastructure.response;

import com.qingshuihe.common.infrastructure.exception.AppexcepitonCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Description:定义返回数据格式，统一使用
 * @Author: shl
 * @Date: 2023/1/28
 **/
@Data
@AllArgsConstructor
public class Resp<T> {
    private int code;
    private String message;
    private T data;


    public static <T> Resp success(){
        Resp resp = new Resp(200, "success", null);
        return resp;
    }
    public static <T> Resp success(T data){
        Resp resp = new Resp(200, "success", data);
        return resp;
    }

    public static <T>Resp success(String message,T data){
        Resp resp = new Resp(200, message, data);
        return resp;
    }

    public static Resp error(AppexcepitonCodeMsg appexcepitonCodeMsg){
        Resp resp = new Resp(appexcepitonCodeMsg.getCode(), appexcepitonCodeMsg.getMessage(), null);
        return resp;
    }

    public static Resp error(int code ,String msg){
        Resp resp = new Resp(code, msg, null);
        return resp;
    }
}
