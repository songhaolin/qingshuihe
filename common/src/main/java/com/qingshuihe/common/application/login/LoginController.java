package com.qingshuihe.common.application.login;

import com.qingshuihe.common.domain.service.UserBusService;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.login.Ilogin;
import com.qingshuihe.common.interfaces.outbond.login.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;
import com.qingshuihe.common.utils.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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
    public ResultDo logout(@RequestBody UserVo userVo) {
        ResultDo<String> stringResultDo = new ResultDo<>();
        stringResultDo.setMessage("登出成功！");
        return stringResultDo;
    }

    @Override
    @PostMapping("/addUser")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/addUser')||hasRole('admin')")
    @ResponseBody
    public ResultDo addUser(@RequestBody RegisterUserVO registerUserVO) {
        ResultDo<String> stringResultDo = new ResultDo<>();
        stringResultDo.setMessage("注册成功！");
        return stringResultDo;
    }


}
