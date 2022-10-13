package com.qingshuihe.common.interfaces.outbond.admin.vo;

import com.qingshuihe.common.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/30
 **/
@ApiModel(value = "角色权限关联信息")
@Data
public class RolePermissionRelationVo extends BaseVo {
    /**
     * @Description: 角色ID
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("角色ID")
    private Long roleId;
    /**
     * @Description: 权限ID
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("权限ID")
    private Long permissionId;
}
