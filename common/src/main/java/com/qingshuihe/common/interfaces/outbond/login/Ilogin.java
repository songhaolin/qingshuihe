package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import io.swagger.annotations.ApiOperation;

public interface Ilogin {
    @ApiOperation("用户登录")
    LoginResultDo login(UserVo userVo);

    @ApiOperation("用户登出")
    LoginResultDo logout(UserVo userVo);

}
