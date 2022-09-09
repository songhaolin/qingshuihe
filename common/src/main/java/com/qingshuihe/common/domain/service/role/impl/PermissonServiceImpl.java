package com.qingshuihe.common.domain.service.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.service.role.PermissionService;
import com.qingshuihe.common.domain.service.role.entity.PermissionEntity;
import com.qingshuihe.common.domain.service.role.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class PermissonServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
}
