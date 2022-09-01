package com.qingshuihe.common.application.login;

import com.qingshuihe.common.domain.service.UserBusService;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.login.Ilogin;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;
import com.qingshuihe.common.utils.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @ResponseBody
    public LoginResultDo logout(@RequestBody UserVo userVo) {
        LoginResultDo loginResultDo = new LoginResultDo();
        loginResultDo.setMessage("登出成功！");
        return loginResultDo;
    }
}
