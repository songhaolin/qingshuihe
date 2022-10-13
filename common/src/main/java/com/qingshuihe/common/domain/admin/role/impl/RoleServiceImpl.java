package com.qingshuihe.common.domain.admin.role.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.role.RoleService;
import com.qingshuihe.common.domain.admin.role.entity.RoleEntity;
import com.qingshuihe.common.domain.admin.role.mapper.RoleMapper;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {


    @Override
    public ResultDo modifyRole(RoleVo roleVo) {
        ResultDo<RoleVo> roleVoResultDo = new ResultDo<>();
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleVo, roleEntity);
        if (!super.saveOrUpdate(roleEntity)) {
            roleVoResultDo.setCode(CommonConstant.STATUS_ERROR);
            roleVoResultDo.setMessage("更改用户角色信息失败！");
        } else {
            BeanUtils.copyProperties(roleEntity, roleVo);
        }
        roleVoResultDo.setObj(roleVo);
        return roleVoResultDo;
    }

    @Override
    public ResultPageDo<RoleVo> queryRole(QueryPageVo<RoleVo> queryPageVo) {
        RoleVo roleVo = queryPageVo.getParamObj();
        Page<RoleEntity> roleEntityPage = new Page<>();
        LambdaQueryWrapper<RoleEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(roleVo.getCode())) {
            queryWrapper.like(RoleEntity::getCode, roleVo.getCode());
        }
        if (StringUtils.isNotEmpty(roleVo.getName())) {
            queryWrapper.like(RoleEntity::getName, roleVo.getName());
        }
        roleEntityPage = super.page(roleEntityPage, queryWrapper);
        ResultPageDo<RoleVo> roleVoResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(roleEntityPage, roleVoResultPageDo);
        return roleVoResultPageDo;
    }
}
