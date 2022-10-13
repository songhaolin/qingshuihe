package com.qingshuihe.common.domain.admin.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.user.UserService;
import com.qingshuihe.common.domain.admin.user.entity.UserEntity;
import com.qingshuihe.common.domain.admin.user.mapper.UserMapper;
import com.qingshuihe.common.interfaces.outbond.admin.vo.LoginUserVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginUserVo findUserByName(String username) {
        UserEntity userEntity = baseMapper.findUserByName(username);
        UserVo userVo = new UserVo();
        userVo.setUsername(userEntity.getUsername());
        userVo.setPassword(userEntity.getPassword());
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setUserVo(userVo);
        //获取用户权限信息
        List ls = baseMapper.getUserPermission(userEntity.getId());
        //获取用户角色信息
        List<String> userRoleList = baseMapper.getUserRole(userEntity.getId());
        for (String role : userRoleList) {
            ls.add(role);
        }
        loginUserVo.setPermissions(ls);
        return loginUserVo;
    }

    @Override
    public ResultDo<RegisterUserVO> modifyUser(RegisterUserVO registerUserVO) {
        ResultDo<RegisterUserVO> registerUserVOResultDo = new ResultDo<>();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(registerUserVO, userEntity);
        //插入数据库的密码应该进行加密,如果外部传入的密码不为空，则需要加密后存储数据库
        if (StringUtils.isNotEmpty(userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        //如果传入的数据已经存在且密码为空，则保持原来的密码不变
        if (StringUtils.isNotEmpty(userEntity.getId())&&StringUtils.isEmpty(userEntity.getPassword())){
            userEntity.setPassword(super.getById(userEntity.getId()).getPassword());
        }
        if (!super.saveOrUpdate(userEntity)) {
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
        userEntityPage = super.page(userEntityPage, queryWrapper);
        ResultPageDo<RegisterUserVO> registerUserVOResultPageDo = new ResultPageDo<>();
        BeanUtils.copyProperties(userEntityPage, registerUserVOResultPageDo);
        return registerUserVOResultPageDo;
    }

}
