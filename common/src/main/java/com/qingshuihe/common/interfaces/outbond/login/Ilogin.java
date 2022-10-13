package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "登录管理")
public interface Ilogin {
    @ApiOperation(value = "/admin/login",notes = "用户登录")
    LoginResultDo login(UserVo userVo);

    @ApiOperation(value = "/admin/logout",notes = "用户登出")
    BaseDto logout();

    @ApiOperation(value = "/admin/modifyUser",notes = "更改用户")
    ResultDo modifyUser(RegisterUserVO registerUserVO);

    @ApiOperation(value = "/admin/queryUser",notes = "查询用户")
    ResultPageDo<RegisterUserVO> queryUser( QueryPageVo<RegisterUserVO> queryPageVo);

    @ApiOperation(value = "/admin/modifyRole",notes = "更改角色")
    ResultDo modifyRole(RoleVo roleVo);

    @ApiOperation(value = "/admin/queryRole",notes = "查询角色信息")
    ResultPageDo<RoleVo> queryRole( QueryPageVo<RoleVo> queryPageVo);

    @ApiOperation(value = "/admin/modifyPermission",notes = "更改权限")
    ResultDo modifyPermission(PermissionVo permissionVo);

    @ApiOperation(value = "/admin/queryPermission",notes = "查询权限信息")
    ResultPageDo<PermissionVo> queryPermission ( QueryPageVo<PermissionVo> queryPageVo);

    @ApiOperation(value = "/admin/modifyRolePermissionRelation",notes = "更改角色权限关联信息")
    ResultDo modifyRolePermissionRelation( RolePermissionRelationVo rolePermissionRelationVo);

    @ApiOperation(value = "/admin/modifyRoleUserRelation",notes = "更改用户角色关联信息")
    ResultDo modifyRoleUserRelation( RoleUserRelationVo roleUserRelationVo);


}
