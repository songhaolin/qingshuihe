package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/13
 **/
@Data
@ApiModel("查询参数对象")
public class QueryPageVo<T> extends ResultPageDo<T> {

    @ApiModelProperty("查询参数对象")
    private T paramObj;

    /**
     * 排序字段信息
     */
    @ApiModelProperty("排序字段信息")
    private List<T> orders = new ArrayList<>();

}
