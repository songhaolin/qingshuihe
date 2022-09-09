package com.qingshuihe.common.infrastructure.security.userservice;

import com.qingshuihe.common.domain.service.user.UserService;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Description:获取userDetails的信息，权限信息
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Override
    public LoginUserVo loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByName(username);
    }
}
