package com.qingshuihe.common.application.login;

import com.qingshuihe.common.domain.service.UserBusService;
import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.interfaces.outbond.login.*;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/29
 **/
@ApiModel(value = "登录管理")
@RestController
@RequestMapping("/admin")
public class LoginController implements Ilogin {

    @Autowired
    private UserBusService userBusService;

    @Override
    @PostMapping("/login")
    @ResponseBody
    public LoginResultDo login(@RequestBody UserVo userVo) {
        return userBusService.login(userVo);
    }

    @Override
    @PostMapping("/logout")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/logout')")
    @ResponseBody
    public BaseDto logout() {
        return userBusService.logout();
    }

    @Override
    @PostMapping("/modifyUser")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/modifyUser')||hasRole('admin')")
    @ResponseBody
    public ResultDo modifyUser(@RequestBody RegisterUserVO registerUserVO) {
        return userBusService.modifyUser(registerUserVO);
    }

    @Override
    @PostMapping("/queryUser")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/queryUser')||hasRole('admin')")
    @ResponseBody
    public ResultPageDo<RegisterUserVO> queryUser(@RequestBody QueryPageVo<RegisterUserVO> queryPageVo) {
        return userBusService.queryUser(queryPageVo);
    }

    @Override
    @PostMapping("/modifyRole")
    @PreAuthorize("hasAnyAuthority('/admin/modifyUser')||hasRole('admin')")
    public ResultDo modifyRole(@RequestBody RoleVo roleVo) {
        return userBusService.modifyRole(roleVo);
    }

    @Override
    @PostMapping("/queryRole")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/queryRole')||hasRole('admin')")
    @ResponseBody
    public ResultPageDo<RoleVo> queryRole(@RequestBody QueryPageVo<RoleVo> queryPageVo) {
        return userBusService.queryRole(queryPageVo);
    }

    @Override
    @PostMapping("/modifyPermission")
    @PreAuthorize("hasAnyAuthority('/admin/modifyPermission')||hasRole('admin')")
    public ResultDo modifyPermission(@RequestBody PermissionVo permissionVo) {
        return userBusService.modifyPermission(permissionVo);
    }

    @Override
    @PostMapping("/queryPermission")
    @PreAuthorize("hasAnyAuthority('/admin/queryPermission')||hasRole('admin')")
    public ResultPageDo<PermissionVo> queryPermission (@RequestBody QueryPageVo<PermissionVo> queryPageVo) {
        return userBusService.queryPermission(queryPageVo);
    }


    @Override
    @PostMapping("/modifyRolePermissionRelation")
    @PreAuthorize("hasAnyAuthority('/admin/modifyRolePermissionRelation')||hasRole('admin')")
    public ResultDo modifyRolePermissionRelation(@RequestBody RolePermissionRelationVo rolePermissionRelationVo) {
        return userBusService.modifyRolePermissionRelation(rolePermissionRelationVo);
    }

    @Override
    @PostMapping("/modifyRoleUserRelation")
    @PreAuthorize("hasAnyAuthority('/admin/modifyRoleUserRelation')||hasRole('admin')")
    public ResultDo modifyRoleUserRelation(@RequestBody RoleUserRelationVo roleUserRelationVo) {
        return userBusService.modifyRoleUserRelation(roleUserRelationVo);
    }


}
