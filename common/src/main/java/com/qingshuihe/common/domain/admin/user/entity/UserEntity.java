package com.qingshuihe.common.domain.admin.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.qingshuihe.common.base.BaseEntity;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Data
@TableName("sys_user_t")
public class UserEntity extends BaseEntity {

    /**
     * @Description: 用户名
     * @Date: 2022/9/5
     * @Param null:
     **/
    private String username;
    /**
     * @Description: 用户密码
     * @Date: 2022/9/5
     * @Param null:
     **/
    private String password;
    /**
     * @Description: 性别，0男1女
     * @Date: 2022/9/5
     * @Param null:
     **/
    private int sex;
    /**
     * @Description: 邮箱
     * @Date: 2022/9/5
     * @Param null:
     **/
    private String email;
    /**
     * @Description: 工号
     * @Date: 2022/9/5
     * @Param null:
     **/
    private String workId;
}
