package com.qingshuihe.common.interfaces.outbond.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/30
 **/
@Data
@ApiModel(value = "返回基本信息")
public class BaseDto {

    /**
     * @Description: 返回状态码
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("返回状态码")
    private int code;
    /**
     * @Description: 返回信息
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("返回信息")
    private String message;
}
