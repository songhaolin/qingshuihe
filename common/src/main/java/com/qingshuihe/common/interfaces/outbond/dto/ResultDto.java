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
@ApiModel(value = "返回结果")
public class ResultDto<T> extends BaseDto {
    @ApiModelProperty("返回数据")
    private T obj;
}
