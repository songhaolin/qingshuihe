package com.qingshuihe.common.domain.service;


import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.login.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.login.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.login.RoleVo;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;

/**
 * @Description: 用户业务相关服务
 * @Date: 2022/8/31
 * @Param null:
 **/
public interface UserBusService {

    LoginResultDo login(UserVo userVo);

    BaseDto logout();

    ResultDo modifyUser(RegisterUserVO registerUserVO);

    ResultDo modifyRole(RoleVo roleVo);

    ResultDo modifyPermission(PermissionVo permissionVo);

}
