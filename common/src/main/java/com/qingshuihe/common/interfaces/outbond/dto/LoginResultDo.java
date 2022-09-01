package com.qingshuihe.common.interfaces.outbond.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/31
 **/
@ApiModel(value = "登陆返回对象")
@Data
public class LoginResultDo extends BaseDto{

    /**
     * @Description: 登陆成功返回的有效的token
     * @Date: 2022/8/31
     * @Param null:
     **/
    @ApiModelProperty("认证通过的有效token")
    private String token;
}


