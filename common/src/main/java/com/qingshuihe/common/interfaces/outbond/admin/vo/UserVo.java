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
@ApiModel(value = "用户登录信息")
@Data
public class UserVo extends BaseVo {
    /**
     * @Description: 登陆用户名
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("用户登录名")
    private String username;
    /**
     * @Description: 登录密码
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("用户登录密码")
    private String password;
}
