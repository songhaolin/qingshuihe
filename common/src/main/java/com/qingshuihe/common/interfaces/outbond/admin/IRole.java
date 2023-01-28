package com.qingshuihe.common.interfaces.outbond.admin;

import com.qingshuihe.common.interfaces.outbond.admin.vo.*;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户角色信息管理")
public interface IRole {

    @ApiOperation(value = "/admin/modifyRole",notes = "更改角色")
    ResultDo modifyRole(RoleVo roleVo);

    @ApiOperation(value = "/admin/queryRole",notes = "查询角色信息")
    ResultPageDo<RoleVo> queryRole( QueryPageVo<RoleVo> queryPageVo);

    @ApiOperation(value = "/admin/modifyRoleUserRelation",notes = "更改用户角色关联信息")
    ResultDo modifyRoleUserRelation( RoleUserRelationVo roleUserRelationVo);

    @ApiOperation(value = "/admin/queryRoleById/{id}",notes = "根据Id查询角色")
    RoleVo queryRoleById( Long id);

    @ApiOperation(value = "/admin/deleteRoleById/{id}",notes = "根据Id删除角色")
    ResultDo deleteRoleById( Long id);

    @ApiOperation(value = "/admin/deleteRoleByIds",notes = "根据Id批量删除角色")
    ResultDo deleteRoleByIds( Long[] ids);
}
