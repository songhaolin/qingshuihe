package com.qingshuihe.common.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/9
 **/
@Data
@ApiModel(value = "BaseVo", description = "返回前端的基类信息")
public class BaseVo {

    /**
     * 数据库唯一标识
     */
    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    Long id;
}
