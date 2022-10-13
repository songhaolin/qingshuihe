package com.qingshuihe.common.domain.admin.login;


import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;

/**
 * @Description: 用户业务相关服务
 * @Date: 2022/8/31
 * @Param null:
 **/
public interface LoginService {

    LoginResultDo login(UserVo userVo);

    BaseDto logout();





}
