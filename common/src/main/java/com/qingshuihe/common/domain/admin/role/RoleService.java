package com.qingshuihe.common.domain.admin.role;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.role.entity.RoleEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
public interface RoleService extends IService<RoleEntity> {
    ResultPageDo<RoleVo> queryRole(QueryPageVo<RoleVo> queryPageVo);


    ResultDo modifyRole(RoleVo roleVo);

}
