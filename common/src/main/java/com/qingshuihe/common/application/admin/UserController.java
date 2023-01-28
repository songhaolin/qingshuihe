package com.qingshuihe.common.application.admin;

import com.qingshuihe.common.domain.admin.user.UserService;
import com.qingshuihe.common.interfaces.outbond.admin.IUser;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/10/13
 **/
@RestController
public class UserController implements IUser {


    @Autowired
    private UserService userService;

    @Override
    @PostMapping("/admin/modifyUser")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/modifyUser')||hasRole('admin')")
    @ResponseBody
    public ResultDo modifyUser(@RequestBody RegisterUserVO registerUserVO) {
        return userService.modifyUser(registerUserVO);
    }

    @Override
    @PostMapping("/admin/queryUser")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/queryUser')||hasRole('admin')")
    @ResponseBody
    public ResultPageDo<RegisterUserVO> queryUser(@RequestBody QueryPageVo<RegisterUserVO> queryPageVo) {
        return userService.queryUser(queryPageVo);
    }

    @PostMapping("/admin/resetpw")
    @Override
    public ResultDo resetpw(String pwd) {
        return userService.resetpw(pwd);
    }

    @PostMapping("/admin/sendResetLink")
    @Override
    public ResultDo sendResetLink(String email) {

        return userService.sendResetLink(email);
    }

    @PostMapping("/admin/sendVerifyCode")
    @Override
    public ResultDo sendVerifyCode(String email) {

        return userService.sendVerifyCode(email);
    }

    @PostMapping("/admin/queryUserById/{id}")
    @Override
    public RegisterUserVO queryUserById(@PathVariable(name = "id") Long id) {
        return userService.queryUserById(id);
    }

    @DeleteMapping("/admin/deleteUserById/{id}")
    @Override
    public ResultDo deleteUserById(@PathVariable(name = "id") Long id) {
        return userService.deleteUserById(id);
    }

    @DeleteMapping("/admin/deleteUserByIds")
    @Override
    public ResultDo deleteUserByIds(Long[] ids) {
        return userService.deleteUserByIds(ids);
    }


}
