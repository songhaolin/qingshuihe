package com.qingshuihe.common.domain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingshuihe.common.domain.service.UserBusService;
import com.qingshuihe.common.domain.service.role.PermissionService;
import com.qingshuihe.common.domain.service.role.RolePermissionRelationService;
import com.qingshuihe.common.domain.service.role.RoleService;
import com.qingshuihe.common.domain.service.role.RoleUserRelationService;
import com.qingshuihe.common.domain.service.role.entity.PermissionEntity;
import com.qingshuihe.common.domain.service.role.entity.RoleEntity;
import com.qingshuihe.common.domain.service.role.entity.RolePermissionRelationEntity;
import com.qingshuihe.common.domain.service.role.entity.RoleUserRelationEntity;
import com.qingshuihe.common.domain.service.user.UserService;
import com.qingshuihe.common.domain.service.user.entity.UserEntity;
import com.qingshuihe.common.interfaces.outbond.dto.BaseDto;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.interfaces.outbond.login.*;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.JWTUtil;
import com.qingshuihe.common.utils.StringUtils;
import com.qingshuihe.common.utils.WebResponseUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Service
public class UserBusServiceImpl implements UserBusService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionRelationService rolePermissionRelationService;

    @Autowired
    private RoleUserRelationService roleUserRelationService;

    @Override
    public LoginResultDo login(UserVo userVo) {
        LoginResultDo loginResultDo = new LoginResultDo();
        //将用户名、密码放入token生成器中，后续会塞入认证管理器中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        /**
         * 将上述包含用户名、密码的token生成器塞入认证管理中，如果认证通过，会获得一个认证管理器对象
         * 其中认证的动作是先根据username获取userDetails对象，然后通过认证管理器去认证用户输入的信息与userDetails信息是否匹配
         * 在UserDetailServiceImpl中实现获取userDetails对象
         * authenticationManager是在AuthorizationServerConfig中注册的一个bean,这里可以直接注入并使用其认证功能)
         **/
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (null == authentication) {
            throw new RuntimeException("认证失败！");
        }
        //认证通过后，从认证结果中取出用户登陆鉴权图中的userDetails对象，这里需要自定义一个LoginUserVo对象继承spring提供的userDetails对象
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        /**
         * 将loginUserVo存在分布式缓存中，当下一次请求时如果带有token，如果token未过期，会从token中解析出username
         * 根据该username从缓存中取出loginUserVo使用，并再次刷新缓存,从而减少与数据库的交互，这就是无状态登陆
         */
        try {
            stringRedisTemplate.opsForValue().set(loginUserVo.getUsername(), JSONObject.toJSONString(loginUserVo));
        } catch (Exception e) {

        }
        //使用username生成token,存入loginResultDo对象中返回请求方，作为下一次请求的凭证
        String token = JWTUtil.createJWT(loginUserVo.getUsername());
        loginResultDo.setToken(token);
        return loginResultDo;
    }

    @Override
    public BaseDto logout() {

        //从security上下文中获取通过JWTFilter过滤的鉴权信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        //清空redis中的用户信息
        stringRedisTemplate.delete(loginUserVo.getUsername());
        BaseDto baseDto = new BaseDto();
        baseDto.setMessage("登出成功！");
        return baseDto;
    }

    @Override
    public ResultDo<RegisterUserVO> modifyUser(RegisterUserVO registerUserVO) {
        ResultDo<RegisterUserVO> registerUserVOResultDo = new ResultDo<>();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerUserVO, userEntity);
        //插入数据库的密码应该进行加密
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if (!userService.saveOrUpdate(userEntity)) {
            registerUserVOResultDo.setCode(CommonConstant.STATUS_ERROR);
            registerUserVOResultDo.setMessage("修改用户信息失败！");
            registerUserVOResultDo.setObj(registerUserVO);
        } else {
            BeanUtils.copyProperties(userEntity, registerUserVO);
            //返回前端的密码应为空
            registerUserVO.setPassword("");
            registerUserVOResultDo.setObj(registerUserVO);
        }
        return registerUserVOResultDo;
    }

    @Override
    public ResultDo modifyRole(RoleVo roleVo) {
        ResultDo<RoleVo> roleVoResultDo = new ResultDo<>();
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties(roleVo, roleEntity);
        if (!roleService.saveOrUpdate(roleEntity)) {
            roleVoResultDo.setCode(CommonConstant.STATUS_ERROR);
            roleVoResultDo.setMessage("更改用户角色信息失败！");
        } else {
            BeanUtils.copyProperties(roleEntity, roleVo);
        }
        roleVoResultDo.setObj(roleVo);
        return roleVoResultDo;
    }

    @Override
    public ResultDo modifyPermission(PermissionVo permissionVo) {
        ResultDo permissionVoResultDo;
        PermissionEntity permissionEntity = new PermissionEntity();
        BeanUtils.copyProperties(permissionVo, permissionEntity);
        if (!permissionService.saveOrUpdate(permissionEntity)) {
            permissionVoResultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更改权限信息失败！", permissionVo);
        } else {
            BeanUtils.copyProperties(permissionEntity, permissionVo);
            permissionVoResultDo = WebResponseUtils.setSuccessResultDo(permissionVo);
        }
        return permissionVoResultDo;
    }

    @Override
    public ResultDo modifyRolePermissionRelation(RolePermissionRelationVo rolePermissionRelationVo) {
        ResultDo resultDo;
        RolePermissionRelationEntity rolePermissionRelationEntity = new RolePermissionRelationEntity();
        BeanUtils.copyProperties(rolePermissionRelationVo, rolePermissionRelationEntity);
        //判断该权限点和角色是否已经关联
        LambdaQueryWrapper<RolePermissionRelationEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RolePermissionRelationEntity::getPermissionId, rolePermissionRelationVo.getPermissionId());
        lambdaQueryWrapper.eq(RolePermissionRelationEntity::getRoleId, rolePermissionRelationVo.getRoleId());
        List<RolePermissionRelationEntity> list = rolePermissionRelationService.list(lambdaQueryWrapper);
        if (StringUtils.isNotEmpty(list)) {
            return WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "该角色下已经具备对应权限，请勿重复添加！", rolePermissionRelationVo);
        }
        //将权限点和角色挂钩入库
        if (!rolePermissionRelationService.saveOrUpdate(rolePermissionRelationEntity)) {
            resultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更新用户权限关联信息失败！", rolePermissionRelationVo);
        } else {
            BeanUtils.copyProperties(rolePermissionRelationEntity, rolePermissionRelationVo);
            resultDo = WebResponseUtils.setSuccessResultDo(rolePermissionRelationVo);
        }
        return resultDo;
    }

    @Override
    public ResultDo modifyRoleUserRelation(RoleUserRelationVo roleUserRelationVo) {
        ResultDo resultDo;
        RoleUserRelationEntity roleUserRelationEntity = new RoleUserRelationEntity();
        BeanUtils.copyProperties(roleUserRelationVo, roleUserRelationEntity);
        if (!roleUserRelationService.saveOrUpdate(roleUserRelationEntity)) {
            resultDo = WebResponseUtils.setResultDo(CommonConstant.STATUS_ERROR, "更新用户角色关联信息失败", roleUserRelationVo);
        } else {
            BeanUtils.copyProperties(roleUserRelationEntity, roleUserRelationVo);
            resultDo = WebResponseUtils.setSuccessResultDo(roleUserRelationVo);
        }
        return resultDo;
    }

    @Override
    public ResultPageDo<RegisterUserVO> queryUser(QueryPageVo<RegisterUserVO> queryPageVo) {
        RegisterUserVO registerUserVO = queryPageVo.getParamObj();
        Page<UserEntity> userEntityPage = new Page<>();
        LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(registerUserVO.getUsername())) {
            queryWrapper.like(UserEntity::getUsername, registerUserVO.getUsername());
        }
        if (StringUtils.isNotEmpty(registerUserVO.getWorkId())) {
            queryWrapper.like(UserEntity::getWorkId, registerUserVO.getWorkId());
        }
        if (StringUtils.isNotEmpty(registerUserVO.getEmail())) {
            queryWrapper.like(UserEntity::getEmail, registerUserVO.getEmail());
        }
        userEntityPage = userService.page(userEntityPage, queryWrapper);
        ResultPageDo<RegisterUserVO> registerUserVOResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(userEntityPage, registerUserVOResultPageDo);
        return registerUserVOResultPageDo;
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
        roleEntityPage = roleService.page(roleEntityPage, queryWrapper);
        ResultPageDo<RoleVo> roleVoResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(roleEntityPage, roleVoResultPageDo);
        return roleVoResultPageDo;
    }

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
        permissionEntityPage = permissionService.page(permissionEntityPage);
        ResultPageDo<PermissionVo> permissionVoResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(permissionEntityPage,permissionVoResultPageDo);
        return permissionVoResultPageDo;
    }
}
