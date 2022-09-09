package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import io.swagger.annotations.ApiOperation;

public interface Ilogin {
    @ApiOperation("用户登录")
    LoginResultDo login(UserVo userVo);

    @ApiOperation("用户登出")
    BaseDto logout();

    @ApiOperation("更改用户")
    ResultDo modifyUser(RegisterUserVO registerUserVO);

    @ApiOperation("更改角色")
    ResultDo modifyRole(RoleVo roleVo);

    @ApiOperation("更改权限")
    ResultDo modifyPermission(PermissionVo permissionVo);

}
