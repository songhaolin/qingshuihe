package com.qingshuihe.common.domain.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.service.role.RoleService;
import com.qingshuihe.common.domain.service.role.entity.RoleEntity;
import com.qingshuihe.common.domain.service.role.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {
}
