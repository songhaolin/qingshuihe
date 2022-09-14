package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.ApiOperation;

public interface Ilogin {
    @ApiOperation("用户登录")
    LoginResultDo login(UserVo userVo);

    @ApiOperation("用户登出")
    BaseDto logout();

    @ApiOperation("更改用户")
    ResultDo modifyUser(RegisterUserVO registerUserVO);

    @ApiOperation("查询用户")
    ResultPageDo<RegisterUserVO> queryUser( QueryPageVo<RegisterUserVO> queryPageVo);

    @ApiOperation("更改角色")
    ResultDo modifyRole(RoleVo roleVo);

    @ApiOperation("查询角色信息")
    ResultPageDo<RoleVo> queryRole( QueryPageVo<RoleVo> queryPageVo);

    @ApiOperation("更改权限")
    ResultDo modifyPermission(PermissionVo permissionVo);

    @ApiOperation("查询权限信息")
    ResultPageDo<PermissionVo> queryPermission ( QueryPageVo<PermissionVo> queryPageVo);

    @ApiOperation("更改角色权限关联信息")
    ResultDo modifyRolePermissionRelation( RolePermissionRelationVo rolePermissionRelationVo);

    @ApiOperation("更改用户角色关联信息")
    ResultDo modifyRoleUserRelation( RoleUserRelationVo roleUserRelationVo);


}
