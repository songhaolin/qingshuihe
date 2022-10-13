package com.qingshuihe.common.domain.admin.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.permission.RolePermissionRelationService;
import com.qingshuihe.common.domain.admin.permission.entity.RolePermissionRelationEntity;
import com.qingshuihe.common.domain.admin.permission.mapper.RolePermissionRelationMapper;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RolePermissionRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.StringUtils;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class RolePermissionRelationServiceImpl extends ServiceImpl<RolePermissionRelationMapper, RolePermissionRelationEntity> implements RolePermissionRelationService {


    @Override
    public ResultDo modifyRolePermissionRelation(RolePermissionRelationVo rolePermissionRelationVo) {
        ResultDo resultDo;
        RolePermissionRelationEntity rolePermissionRelationEntity = new RolePermissionRelationEntity();
        BeanUtils.copyProperties(rolePermissionRelationVo, rolePermissionRelationEntity);
        //判断该权限点和角色是否已经关联
        LambdaQueryWrapper<RolePermissionRelationEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermissionRelationEntity::getPermissionId, rolePermissionRelationVo.getPermissionId());
        lambdaQueryWrapper.eq(RolePermissionRelationEntity::getRoleId, rolePermissionRelationVo.getRoleId());
        List<RolePermissionRelationEntity> list = super.list(lambdaQueryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            return WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "该角色下已经具备对应权限，请勿重复添加！", rolePermissionRelationVo);
        }
        //将权限点和角色挂钩入库
        if (!super.saveOrUpdate(rolePermissionRelationEntity)) {
            resultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更新用户权限关联信息失败！", rolePermissionRelationVo);
        } else {
            BeanUtils.copyProperties(rolePermissionRelationEntity, rolePermissionRelationVo);
            resultDo = WebResponseUtils.setSuccessResultDo(rolePermissionRelationVo);
        }
        return resultDo;
    }
}
