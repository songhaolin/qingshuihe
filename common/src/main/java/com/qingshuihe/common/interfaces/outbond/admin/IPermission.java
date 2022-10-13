package com.qingshuihe.common.interfaces.outbond.admin;

import com.qingshuihe.common.interfaces.outbond.admin.vo.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RolePermissionRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户权限信息管理")
public interface IPermission {

    @ApiOperation(value = "/admin/modifyPermission",notes = "更改权限")
    ResultDo modifyPermission(PermissionVo permissionVo);

    @ApiOperation(value = "/admin/queryPermission",notes = "查询权限信息")
    ResultPageDo<PermissionVo> queryPermission ( QueryPageVo<PermissionVo> queryPageVo);

    @ApiOperation(value = "/admin/modifyRolePermissionRelation",notes = "更改角色权限关联信息")
    ResultDo modifyRolePermissionRelation( RolePermissionRelationVo rolePermissionRelationVo);

}
