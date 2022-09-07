package com.qingshuihe.common.domain.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.service.user.entity.UserEntity;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
/**
 * @author song
 */
public interface UserService extends IService<UserEntity> {
    LoginUserVo findUserByName(String username);
}
