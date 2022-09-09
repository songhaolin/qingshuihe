package com.qingshuihe.common.domain.service.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:角色实体类
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Data
@TableName("sys_role_t")
public class RoleEntity extends BaseEntity {


    /**
     * @Description: 角色名
     * @Date: 2022/9/8
     **/
    private String name;
    /**
     * @Description: 角色代码
     * @Date: 2022/9/8
     **/
    private String code;
}
