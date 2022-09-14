package com.qingshuihe.common.interfaces.outbond.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/13
 **/
@Data
@ApiModel("查询返回结果")
public class ResultPageDo<T> {

    @ApiModelProperty("业务数据查询结果")
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @ApiModelProperty("总数")
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    @ApiModelProperty("每页显示条数")
    private long size = 10;

    /**
     * 当前页
     */
    @ApiModelProperty("当前页")
    private long current = 1;


}
