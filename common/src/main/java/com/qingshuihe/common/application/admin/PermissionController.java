package com.qingshuihe.common.application.admin;

import com.qingshuihe.common.domain.admin.permission.PermissionService;
import com.qingshuihe.common.domain.admin.permission.RolePermissionRelationService;
import com.qingshuihe.common.interfaces.outbond.admin.IPermission;
import com.qingshuihe.common.interfaces.outbond.admin.vo.PermissionVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RolePermissionRelationVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/10/13
 **/

@RestController
public class PermissionController implements IPermission {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    @Override
    @PostMapping("/admin/modifyPermission")
    @PreAuthorize("hasAnyAuthority('/admin/modifyPermission')||hasRole('admin')")
    public ResultDo modifyPermission(@RequestBody PermissionVo permissionVo) {
        return permissionService.modifyPermission(permissionVo);
    }

    @Override
    @PostMapping("/admin/queryPermission")
    @PreAuthorize("hasAnyAuthority('/admin/queryPermission')||hasRole('admin')")
    public ResultPageDo<PermissionVo> queryPermission (@RequestBody QueryPageVo<PermissionVo> queryPageVo) {
        return permissionService.queryPermission(queryPageVo);
    }


    @Override
    @PostMapping("/admin/modifyRolePermissionRelation")
    @PreAuthorize("hasAnyAuthority('/admin/modifyRolePermissionRelation')||hasRole('admin')")
    public ResultDo modifyRolePermissionRelation(@RequestBody RolePermissionRelationVo rolePermissionRelationVo) {
        return rolePermissionRelationService.modifyRolePermissionRelation(rolePermissionRelationVo);
    }
}
