package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.ResultDto;
import io.swagger.annotations.ApiOperation;

public interface Ilogin {
    @ApiOperation("用户登录")
    ResultDto<String> login(UserVo userVo);
    @ApiOperation("用户登出")
    ResultDto<String> logout(UserVo userVo);

}
