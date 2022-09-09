package com.qingshuihe.common.domain.service.role.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:权限实体类
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Data
@TableName("sys_permission_t")
public class PermissionEntity extends BaseEntity {


    /**
     * @Description: 权限对应路径
     * @Date: 2022/9/8
     **/
    private String url;
    /**
     * @Description: 权限描述
     * @Date: 2022/9/8
     **/
    private String description;
}
