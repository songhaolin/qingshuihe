package com.qingshuihe.common.application.admin;

import com.qingshuihe.common.domain.admin.login.LoginService;
import com.qingshuihe.common.infrastructure.exception.Resp;
import com.qingshuihe.common.interfaces.outbond.admin.ILogin;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/29
 **/
@RestController
public class LoginController implements ILogin {

    @Autowired
    private LoginService loginService;

    @Override
    @PostMapping("/admin/login")
//    @LogAnnotation
    public Resp<String> login(@RequestBody UserVo userVo) {
        return loginService.login(userVo);
    }

    @Override
    @PostMapping("/admin/logout")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/logout')")
    public Resp logout() {
        return loginService.logout();
    }










}
