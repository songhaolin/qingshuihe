package com.qingshuihe.common.interfaces.outbond.admin.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/2
 **/
@Data
@ApiModel(value = "RegisterUserVO",description = "用户注册信息")
public class RegisterUserVO extends UserVo{

    @ApiModelProperty("性别 0:男,1:女")
    private int sex;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("工号")
    private String workId;
}
