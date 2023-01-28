package com.qingshuihe.common.domain.admin.login;


import com.qingshuihe.common.infrastructure.response.Resp;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;

/**
 * @Description: 用户业务相关服务
 * @Date: 2022/8/31
 * @Param null:
 **/
public interface LoginService {

    Resp<String> login(UserVo userVo);

    Resp<String> logout();





}
