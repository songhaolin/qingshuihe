package com.qingshuihe.common.domain.admin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingshuihe.common.domain.admin.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    UserEntity findUserByName(String username);

    List<String> getUserPermission(@Param("userId") Long userId);
    List<String> getUserRole(@Param("userId") Long userId);
}
