package com.qingshuihe.common.infrastructure.security.userservice;

import com.qingshuihe.common.domain.service.user.UserService;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Description:获取userDetails的信息，权限信息
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;
    @Override
    public LoginUserVo loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByName(username);
//        LoginUserVo loginUserVo = new LoginUserVo();
//        //这里新建一个用户对象，用来封装用户在数据库中的信息,这里不用数据库实体对象而用返回前端的Vo对象
//        UserVo userVo = new UserVo();
//        userVo.setUsername(username);
//        //这里如果不对数据库密码做加密处理的话，无法通过认证管理器的判断，与视频中有出入
//        userVo.setPassword(passwordEncoder.encode(CommonConstant.PASS_WORD));
//        //设置权限信息
//        List<String> list = new ArrayList<>();
//        list.add("/admin/logout");
//        list.add("ROLE_admin");
//        loginUserVo.setUserVo(userVo);
//        loginUserVo.setPermissions(list);
//        return loginUserVo;
    }
}
