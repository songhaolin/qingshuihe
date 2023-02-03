package com.qingshuihe.common.domain.admin.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.permission.entity.PermissionEntity;
import com.qingshuihe.common.domain.admin.permission.mapper.PermissionMapper;
import com.qingshuihe.common.interfaces.outbond.admin.vo.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

class PermissonServiceImplTest extends BaseTest{

    @InjectMocks
    @Spy
    private PermissonServiceImpl permissionService;
    @Mock
    private IService iService;
    @Mock
    private PermissionMapper permissionMapper;


    @Test
    void queryPermission() {
        QueryPageVo<PermissionVo> queryPageVo = new QueryPageVo<>();
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setUrl("admin");
        permissionVo.setDescription("111");
        queryPageVo.setParamObj(permissionVo);
        Page<PermissionEntity> permissionEntityPage = new Page<>();
        PermissionEntity permissionEntity = new PermissionEntity();
        permissionEntity.setId(1l);
        permissionEntity.setUrl("/admin");
        permissionEntity.setDescription("111");
        List<PermissionEntity> list = new ArrayList<>();
        list.add(permissionEntity);
        permissionEntityPage.setRecords(list);
        //构造需要mock的参数
        Page<PermissionEntity> permissionEntityPage111 = new Page<>();
        LambdaQueryWrapper<PermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(PermissionEntity::getUrl,permissionVo.getUrl());
        lambdaQueryWrapper.like(PermissionEntity::getDescription,permissionVo.getDescription());
        Mockito.when(permissionService.page(Mockito.any())).thenReturn(permissionEntityPage);
        ResultPageDo<PermissionVo> result = permissionService.queryPermission(queryPageVo);
        Assertions.assertEquals(1,result.getRecords().size());
    }

    @Test
    void modifyPermission() {
        PermissionVo permissionVo = new PermissionVo();
        permissionVo.setUrl("admin");
        permissionVo.setDescription("111");
        Mockito.when(permissionService.saveOrUpdate(Mockito.any(PermissionEntity.class))).thenReturn(true);
        ResultDo resultDo = permissionService.modifyPermission(permissionVo);
        Assertions.assertEquals(200,resultDo.getCode());
        Mockito.when(permissionService.saveOrUpdate(Mockito.any(PermissionEntity.class))).thenReturn(false);
        ResultDo resultDo1 = permissionService.modifyPermission(permissionVo);
        Assertions.assertEquals(201,resultDo1.getCode());

    }
}