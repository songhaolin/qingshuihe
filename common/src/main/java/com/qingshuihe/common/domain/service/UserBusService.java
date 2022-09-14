package com.qingshuihe.common.domain.service;


import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.interfaces.outbond.login.*;

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

    ResultDo modifyRolePermissionRelation(RolePermissionRelationVo rolePermissionRelationVo);

    ResultDo modifyRoleUserRelation(RoleUserRelationVo roleUserRelationVo);

    ResultPageDo<RegisterUserVO> queryUser( QueryPageVo<RegisterUserVO> queryPageVo);

    ResultPageDo<RoleVo> queryRole( QueryPageVo<RoleVo> queryPageVo);

    ResultPageDo<PermissionVo> queryPermission( QueryPageVo<PermissionVo> queryPageVo);
}
