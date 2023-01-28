package com.qingshuihe.common.interfaces.outbond.admin;

import com.qingshuihe.common.infrastructure.response.Resp;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "用户登录管理")
public interface ILogin {
    @ApiOperation(value = "/admin/login",notes = "用户登录")
    Resp login(UserVo userVo);

    @ApiOperation(value = "/admin/logout",notes = "用户登出")
    Resp logout();
}
