package com.qingshuihe.common.infrastructure.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:权限不足异常处理器，统一对出现权限不足的请求做处理
 * @Author: shl
 * @Date: 2022/9/2
 **/
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        BaseDto baseDto = new BaseDto();
        baseDto.setCode(HttpStatus.METHOD_NOT_ALLOWED.value());
        baseDto.setMessage("当前权限不足，如需访问请找管理员授权！");
        WebResponseUtils.renderResponse(httpServletResponse, JSONObject.toJSONString(baseDto));
    }
}
