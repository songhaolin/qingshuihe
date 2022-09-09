package com.qingshuihe.common.domain.service.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:用户角色映射实体类
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Data
@TableName("sys_role_user_relation_t")
public class RoleUserRelationEntity extends BaseEntity {


    /**
     * @Description: 角色ID
     * @Date: 2022/9/8
     **/
    private long roleId;
    /**
     * @Description: 用户ID
     * @Date: 2022/9/8
     **/
    private long userId;
}
