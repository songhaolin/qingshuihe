package com.qingshuihe.common.domain.admin.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.permission.entity.RolePermissionRelationEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RolePermissionRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
public interface RolePermissionRelationService extends IService<RolePermissionRelationEntity> {

    ResultDo modifyRolePermissionRelation(RolePermissionRelationVo rolePermissionRelationVo);

}
