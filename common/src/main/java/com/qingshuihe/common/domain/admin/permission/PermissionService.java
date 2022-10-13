package com.qingshuihe.common.domain.admin.permission;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.permission.entity.PermissionEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/8
 **/
public interface PermissionService extends IService<PermissionEntity> {

    ResultDo modifyPermission(PermissionVo permissionVo);


    ResultPageDo<PermissionVo> queryPermission(QueryPageVo<PermissionVo> queryPageVo);

}
