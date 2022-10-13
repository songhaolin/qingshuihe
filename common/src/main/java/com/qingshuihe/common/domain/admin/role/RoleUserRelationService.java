package com.qingshuihe.common.domain.admin.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.role.entity.RoleUserRelationEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleUserRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
public interface RoleUserRelationService extends IService<RoleUserRelationEntity> {


    ResultDo modifyRoleUserRelation(RoleUserRelationVo roleUserRelationVo);
}
