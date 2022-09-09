package com.qingshuihe.common.domain.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.service.user.entity.UserEntity;
import com.qingshuihe.common.domain.service.user.UserService;
import com.qingshuihe.common.domain.service.user.mapper.UserMapper;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
import com.qingshuihe.common.interfaces.outbond.login.UserVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Override
    public LoginUserVo findUserByName(String username) {
        UserEntity userEntity = baseMapper.findUserByName(username);
        UserVo userVo = new UserVo();
        userVo.setUsername(userEntity.getUsername());
        userVo.setPassword(userEntity.getPassword());
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setUserVo(userVo);
        List ls = new ArrayList<String>();
        ls.add("/admin/logout");
        ls.add("ROLE_admin");
        loginUserVo.setPermissions(ls);
        return loginUserVo;
    }
}
