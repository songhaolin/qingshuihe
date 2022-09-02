package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import io.swagger.annotations.ApiOperation;

public interface Ilogin {
    @ApiOperation("用户登录")
    LoginResultDo login(UserVo userVo);

    @ApiOperation("用户登出")
    ResultDo logout(UserVo userVo);

    @ApiOperation("注册用户")
    ResultDo addUser(RegisterUserVO registerUserVO);

}
