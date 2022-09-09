package com.qingshuihe.common.interfaces.outbond.login;

import com.qingshuihe.common.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/30
 **/
@ApiModel(value = "用户角色信息")
@Data
public class RoleVo extends BaseVo {
    /**
     * @Description: 登陆用户名
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("角色名称")
    private String name;
    /**
     * @Description: 登录密码
     * @Date: 2022/8/30
     * @Param null:
     **/
    @ApiModelProperty("角色代码")
    private String code;
}
