package com.qingshuihe.common.domain.admin.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingshuihe.common.domain.admin.user.UserService;
import com.qingshuihe.common.domain.admin.user.entity.UserEntity;
import com.qingshuihe.common.domain.admin.user.mapper.UserMapper;
import com.qingshuihe.common.infrastructure.common.ResetPasswordProperties;
import com.qingshuihe.common.infrastructure.mail.EmailHandler;
import com.qingshuihe.common.infrastructure.mail.vo.MyMailVo;
import com.qingshuihe.common.infrastructure.util.VerifyCodeUtil;
import com.qingshuihe.common.interfaces.outbond.admin.vo.LoginUserVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.JWTUtil;
import com.qingshuihe.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/9/5
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ResetPasswordProperties resetPasswordProperties;
    @Autowired
    private EmailHandler emailHandler;

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

    @Override
    public ResultDo sendResetLink(String email) {
        ResultDo resultDo = new ResultDo();
        //根据邮件地址判断用户是否存在
        QueryPageVo<RegisterUserVO> queryPageVo = new QueryPageVo<>();
        RegisterUserVO registerUserVO = new RegisterUserVO();
        registerUserVO.setEmail(email);
        queryPageVo.setParamObj(registerUserVO);
        ResultPageDo<RegisterUserVO> registerUserVOResultPageDo = queryUser(queryPageVo);
        if (registerUserVOResultPageDo.getRecords()==null||registerUserVOResultPageDo.getRecords().isEmpty()){
            resultDo.setCode(CommonConstant.STATUS_ERROR);
            resultDo.setMessage("不存在对应用户信息，请核实邮箱后重试");
            return resultDo;
        }
        BeanUtils.copyProperties(registerUserVOResultPageDo.getRecords().get(0),registerUserVO);
        //根据该用户名生成token并存在redis中，方便后续更改密码时判断有效性
        String token = JWTUtil.createJWT(registerUserVO.getUsername());
        stringRedisTemplate.opsForValue().set(registerUserVO.getUsername(), JSONObject.toJSONString(registerUserVO),resetPasswordProperties.getVaildTime(), TimeUnit.MINUTES);
        //生成验证码，替换邮件格式中的信息，同时组装邮件vo
        MyMailVo myMailVo = new MyMailVo();
        myMailVo.setSubject(resetPasswordProperties.getSubject());
        myMailVo.setTo(email);
        String frontPage = resetPasswordProperties.getFrontPage()+token;
        String text = resetPasswordProperties.getHtml().replace(CommonConstant.REST_USER_NAME,registerUserVO.getUsername())
                .replace(CommonConstant.FRONT_PAGE_SETPW,frontPage)
                .replace(CommonConstant.VAILD_TIME,String.valueOf(resetPasswordProperties.getVaildTime()));
        myMailVo.setText(text);
        emailHandler.sendMimeMail(myMailVo,false);
        //发送重置密码邮件
        return resultDo;
    }

    @Override
    public ResultDo sendVerifyCode(String email) {
        ResultDo resultDo = new ResultDo<>();
        String code = "";
        //while（true）的写法在多机器大批量请求的情况下可能出现死机的情况，考虑是否有更好的方案
        while (true){
            code = VerifyCodeUtil.generateVerifyCode();
            //利用redis保证验证码的全局唯一以及加锁，
            if (stringRedisTemplate.opsForValue().setIfAbsent(code,code,resetPasswordProperties.getVaildTime(),TimeUnit.MINUTES)){
                break;
            }
        }
        MyMailVo myMailVo = new MyMailVo();
        myMailVo.setTo(email);
        String text = resetPasswordProperties.getText().replace(CommonConstant.VERIFY_CODE,code)
                .replace(CommonConstant.VAILD_TIME,String.valueOf(resetPasswordProperties.getVaildTime()));
        myMailVo.setText(text);
        myMailVo.setSubject(resetPasswordProperties.getSubject());
        emailHandler.sendSimpleMail(myMailVo);
        return resultDo;
    }

    @Override
    public ResultDo resetpw(String pwd) {
        ResultDo resultDo = new ResultDo();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) usernamePasswordAuthenticationToken.getPrincipal();
        RegisterUserVO registerUserVO = new RegisterUserVO();
        registerUserVO.setId(loginUserVo.getId());
        registerUserVO.setPassword(pwd);
        ResultDo<RegisterUserVO> registerUserVOResultDo = modifyUser(registerUserVO);
        resultDo.setCode(registerUserVOResultDo.getCode());
        resultDo.setMessage(registerUserVOResultDo.getMessage());
        stringRedisTemplate.delete(registerUserVO.getUsername());
        return resultDo;
    }

    @Override
    public RegisterUserVO queryUserById(Long id) {
        UserEntity userEntity = super.getById(id);
        RegisterUserVO userVo = new RegisterUserVO();
        BeanUtils.copyProperties(userEntity,userVo);
        return userVo;
    }

    @Override
    public ResultDo deleteUserById(Long id) {
        ResultDo resultDo = new ResultDo<>();
        if (!super.removeById(id)){
            resultDo.setCode(CommonConstant.STATUS_ERROR);
            resultDo.setMessage("删除用户失败");
        }
        return resultDo;
    }

    @Override
    public ResultDo deleteUserByIds(Long[] ids) {
        ResultDo resultDo = new ResultDo<>();
        if (!super.removeByIds(Arrays.asList(ids))){
            resultDo.setCode(CommonConstant.STATUS_ERROR);
            resultDo.setMessage("批量删除用户失败");
        }
        return resultDo;
    }

}
