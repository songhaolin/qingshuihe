package com.qingshuihe.common.domain.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingshuihe.common.domain.service.user.entity.UserEntity;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
public interface UserMapper extends BaseMapper<UserEntity> {

    UserEntity findUserByName(String username);
}
