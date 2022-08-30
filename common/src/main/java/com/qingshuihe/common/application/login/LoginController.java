package com.qingshuihe.common.application.login;

import com.qingshuihe.common.interfaces.outbond.dto.ResultDto;
import com.qingshuihe.common.interfaces.outbond.login.Ilogin;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;
import com.qingshuihe.common.utils.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
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


    @Override
    @PostMapping("/login")
    @ResponseBody
    public ResultDto<String> login(@RequestBody UserVo userVo) {

        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.setMessage("登录成功！");
        resultDto.setObj("username:"+userVo.getUsername()+",password:"+userVo.getPassword());
        return resultDto;
    }

    @Override
    @PostMapping("/logout")
    @ResponseBody
    public ResultDto<String> logout(@RequestBody UserVo userVo) {
        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.setMessage("登出成功！");
        resultDto.setObj("username:"+userVo.getUsername()+",password:"+userVo.getPassword());
        return resultDto;
    }
}
