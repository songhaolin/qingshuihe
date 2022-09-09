package com.qingshuihe.common.domain.service.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:角色权限映射实体类
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Data
@TableName("sys_role_permission_relation_t")
public class RolePermissionRelationEntity extends BaseEntity {


    /**
     * @Description: 角色ID
     * @Date: 2022/9/8
     **/
    private long roleId;
    /**
     * @Description: 权限ID
     * @Date: 2022/9/8
     **/
    private long permissionId;
}
