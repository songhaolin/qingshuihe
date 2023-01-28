package com.qingshuihe.common.interfaces.outbond.admin;

import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户基本信息管理")
public interface IUser {
    @ApiOperation(value = "/admin/modifyUser",notes = "更改用户")
    ResultDo modifyUser(RegisterUserVO registerUserVO);

    @ApiOperation(value = "/admin/queryUser",notes = "查询用户")
    ResultPageDo<RegisterUserVO> queryUser( QueryPageVo<RegisterUserVO> queryPageVo);

    @ApiOperation(value = "/admin/resetpw",notes = "重置密码")
    ResultDo resetpw( String pwd);

    @ApiOperation(value = "/admin/sendResetLink",notes = "发送重置链接")
    ResultDo sendResetLink( String email);

    @ApiOperation(value = "/admin/sendVerifyCode",notes = "发送验证码")
    ResultDo sendVerifyCode( String email);

    @ApiOperation(value = "/admin/queryUserById/{id}",notes = "根据Id查询角色")
    UserVo queryUserById(Long id);

    @ApiOperation(value = "/admin/deleteUserById/{id}",notes = "根据Id删除角色")
    ResultDo deleteUserById( Long id);

    @ApiOperation(value = "/admin/deleteUserByIds",notes = "根据Id批量删除角色")
    ResultDo deleteUserByIds( Long[] ids);
}
