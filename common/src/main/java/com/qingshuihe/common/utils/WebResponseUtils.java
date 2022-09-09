package com.qingshuihe.common.utils;

import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:输出流工具类，用以封装直接在页面展示的返回信息
 * @Author: shl
 * @Date: 2022/9/2
 **/
public class WebResponseUtils {

    public static void renderResponse(HttpServletResponse httpServletResponse,String message) throws IOException {
        //设置请求状态
        httpServletResponse.setStatus(CommonConstant.STATUS_SUCCESS);
        //设置返回消息格式
        httpServletResponse.setContentType("application/json");
        //设置编码格式
        httpServletResponse.setCharacterEncoding("utf-8");
        //通过流输出的页面
        httpServletResponse.getWriter().print(message);
    }
    /**
     * @Description: 格式化设置返回实体信息
     * @Date: 2022/9/9
     * @Param code:
     * @Param message:
     * @Param t:
     **/
    public static <T> ResultDo<T> setResultDo(int code, String message, T t){
        ResultDo resultDo = new ResultDo<T>();
        resultDo.setCode(code);
        resultDo.setMessage(message);
        resultDo.setObj(t);
        return resultDo;
    }
    /**
     * @Description: 格式化返回成功信息
     * @Date: 2022/9/9
     * @Param t:
     **/
    public static <T> ResultDo<T> setSuccessResultDo( T t){
        ResultDo resultDo = new ResultDo<T>();
        resultDo.setObj(t);
        return resultDo;
    }
}
