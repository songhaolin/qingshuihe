package com.qingshuihe.common.domain.admin.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.permission.PermissionService;
import com.qingshuihe.common.domain.admin.permission.entity.PermissionEntity;
import com.qingshuihe.common.domain.admin.permission.mapper.PermissionMapper;
import com.qingshuihe.common.interfaces.outbond.admin.vo.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.StringUtils;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class PermissonServiceImpl extends ServiceImpl<PermissionMapper, PermissionEntity> implements PermissionService {
    @Override
    public ResultPageDo<PermissionVo> queryPermission(QueryPageVo<PermissionVo> queryPageVo) {
        PermissionVo permissionVo = queryPageVo.getParamObj();
        Page<PermissionEntity> permissionEntityPage = new Page<>();
        LambdaQueryWrapper<PermissionEntity> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotEmpty(permissionVo.getUrl())){
            lambdaQueryWrapper.like(PermissionEntity::getUrl,permissionVo.getUrl());
        }
        if (StringUtils.isNotEmpty(permissionVo.getDescription())){
            lambdaQueryWrapper.like(PermissionEntity::getDescription,permissionVo.getDescription());
        }
        permissionEntityPage = this.page(permissionEntityPage,lambdaQueryWrapper);
        ResultPageDo<PermissionVo> permissionVoResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(permissionEntityPage,permissionVoResultPageDo);
        return permissionVoResultPageDo;
    }

    @Override
    public ResultDo modifyPermission(PermissionVo permissionVo) {
        ResultDo permissionVoResultDo;
        PermissionEntity permissionEntity = new PermissionEntity();
        BeanUtils.copyProperties(permissionVo, permissionEntity);
        if (!this.saveOrUpdate(permissionEntity)) {
            permissionVoResultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更改权限信息失败！", permissionVo);
        } else {
            BeanUtils.copyProperties(permissionEntity, permissionVo);
            permissionVoResultDo = WebResponseUtils.setSuccessResultDo(permissionVo);
        }
        return permissionVoResultDo;
    }

}
