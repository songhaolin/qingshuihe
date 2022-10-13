package com.qingshuihe.common.domain.admin.role.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.role.mapper.RoleUserRelationMapper;
import com.qingshuihe.common.domain.admin.role.RoleUserRelationService;
import com.qingshuihe.common.domain.admin.role.entity.RoleUserRelationEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleUserRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Service
public class RoleUserRelationServiceImpl extends ServiceImpl<RoleUserRelationMapper, RoleUserRelationEntity> implements RoleUserRelationService {


    @Override
    public ResultDo modifyRoleUserRelation(RoleUserRelationVo roleUserRelationVo) {
        ResultDo resultDo;
        RoleUserRelationEntity roleUserRelationEntity = new RoleUserRelationEntity();
        BeanUtils.copyProperties(roleUserRelationVo, roleUserRelationEntity);
        if (!super.saveOrUpdate(roleUserRelationEntity)) {
            resultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更新用户角色关联信息失败", roleUserRelationVo);
        } else {
            BeanUtils.copyProperties(roleUserRelationEntity, roleUserRelationVo);
            resultDo = WebResponseUtils.setSuccessResultDo(roleUserRelationVo);
        }
        return resultDo;
    }
}
