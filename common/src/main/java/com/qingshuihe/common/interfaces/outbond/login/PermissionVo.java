package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/9
 **/
@ApiModel(value = "用户权限信息")
@Data
public class PermissionVo extends BaseVo {

    @ApiModelProperty("权限链接")
    private String url;

    @ApiModelProperty("权限描述")
    private String description;
}
