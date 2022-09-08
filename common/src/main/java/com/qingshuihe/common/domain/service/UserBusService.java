package com.qingshuihe.common.domain.service;


import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;

/**
 * @Description: 用户业务相关服务
 * @Date: 2022/8/31
 * @Param null:
 **/
public interface UserBusService {

    LoginResultDo login(UserVo userVo);

    BaseDto logout();

}
