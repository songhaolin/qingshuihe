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
        //????????????????????????
        List ls = baseMapper.getUserPermission(userEntity.getId());
        //????????????????????????
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
        //??????????????????????????????????????????,????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotEmpty(userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        //?????????????????????????????????????????????????????????????????????????????????
        if (StringUtils.isNotEmpty(userEntity.getId())&&StringUtils.isEmpty(userEntity.getPassword())){
            userEntity.setPassword(super.getById(userEntity.getId()).getPassword());
        }
        if (!super.saveOrUpdate(userEntity)) {
            registerUserVOResultDo.setCode(CommonConstant.STATUS_ERROR);
            registerUserVOResultDo.setMessage("???????????????????????????");
            registerUserVOResultDo.setObj(registerUserVO);
        } else {
            BeanUtils.copyProperties(userEntity, registerUserVO);
            //??????????????????????????????
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
        //??????????????????????????????????????????
        QueryPageVo<RegisterUserVO> queryPageVo = new QueryPageVo<>();
        RegisterUserVO registerUserVO = new RegisterUserVO();
        registerUserVO.setEmail(email);
        queryPageVo.setParamObj(registerUserVO);
        ResultPageDo<RegisterUserVO> registerUserVOResultPageDo = queryUser(queryPageVo);
        if (registerUserVOResultPageDo.getRecords()==null||registerUserVOResultPageDo.getRecords().isEmpty()){
            resultDo.setCode(CommonConstant.STATUS_ERROR);
            resultDo.setMessage("??????????????????????????????????????????????????????");
            return resultDo;
        }
        BeanUtils.copyProperties(registerUserVOResultPageDo.getRecords().get(0),registerUserVO);
        //????????????????????????token?????????redis????????????????????????????????????????????????
        String token = JWTUtil.createJWT(registerUserVO.getUsername());
        stringRedisTemplate.opsForValue().set(registerUserVO.getUsername(), JSONObject.toJSONString(registerUserVO),resetPasswordProperties.getVaildTime(), TimeUnit.MINUTES);
        //?????????????????????????????????????????????????????????????????????vo
        MyMailVo myMailVo = new MyMailVo();
        myMailVo.setSubject(resetPasswordProperties.getSubject());
        myMailVo.setTo(email);
        String frontPage = resetPasswordProperties.getFrontPage()+token;
        String text = resetPasswordProperties.getHtml().replace(CommonConstant.REST_USER_NAME,registerUserVO.getUsername())
                .replace(CommonConstant.FRONT_PAGE_SETPW,frontPage)
                .replace(CommonConstant.VAILD_TIME,String.valueOf(resetPasswordProperties.getVaildTime()));
        myMailVo.setText(text);
        emailHandler.sendMimeMail(myMailVo,false);
        //????????????????????????
        return resultDo;
    }

    @Override
    public ResultDo sendVerifyCode(String email) {
        ResultDo resultDo = new ResultDo<>();
        String code = "";
        //while???true???????????????????????????????????????????????????????????????????????????????????????????????????????????????
        while (true){
            code = VerifyCodeUtil.generateVerifyCode();
            //??????redis?????????????????????????????????????????????
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

}
