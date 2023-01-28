package com.qingshuihe.common.application.admin;

import com.qingshuihe.common.domain.admin.role.RoleService;
import com.qingshuihe.common.domain.admin.role.RoleUserRelationService;
import com.qingshuihe.common.interfaces.outbond.admin.IRole;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleUserRelationVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RoleVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/10/13
 **/
@RestController
public class RoleController implements IRole {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleUserRelationService roleUserRelationService;

    @Override
    @PostMapping("/admin/modifyRole")
    @PreAuthorize("hasAnyAuthority('/admin/modifyUser')||hasRole('admin')")
    public ResultDo modifyRole(@RequestBody RoleVo roleVo) {
        return roleService.modifyRole(roleVo);
    }

    @Override
    @PostMapping("/admin/queryRole")
    //鉴权配置，需要在进入借口之前判断当前访问的接口是否在用户的权限集合中
    @PreAuthorize("hasAnyAuthority('/admin/queryRole')||hasRole('admin')")
    @ResponseBody
    public ResultPageDo<RoleVo> queryRole(@RequestBody QueryPageVo<RoleVo> queryPageVo) {
        return roleService.queryRole(queryPageVo);
    }

    @Override
    @PostMapping("/admin/modifyRoleUserRelation")
    @PreAuthorize("hasAnyAuthority('/admin/modifyRoleUserRelation')||hasRole('admin')")
    public ResultDo modifyRoleUserRelation(@RequestBody RoleUserRelationVo roleUserRelationVo) {
        return roleUserRelationService.modifyRoleUserRelation(roleUserRelationVo);
    }

    @Override
    public RoleVo queryRoleById(Long id) {
        return null;
    }

    @Override
    public ResultDo deleteRoleById(Long id) {
        return null;
    }

    @Override
    public ResultDo deleteRoleByIds(Long[] ids) {
        return null;
    }
}
