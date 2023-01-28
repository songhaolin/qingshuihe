package com.qingshuihe.common.domain.admin.login;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.infrastructure.exception.AppexcepitonCodeMsg;
import com.qingshuihe.common.infrastructure.exception.Appexception;
import com.qingshuihe.common.infrastructure.response.Resp;
import com.qingshuihe.common.interfaces.outbond.admin.vo.LoginUserVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.UserVo;
import com.qingshuihe.common.interfaces.outbond.dto.LoginResultDo;
import com.qingshuihe.common.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public Resp<String> login(UserVo userVo) {
        //将用户名、密码放入token生成器中，后续会塞入认证管理器中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        /**
         * 将上述包含用户名、密码的token生成器塞入认证管理中，如果认证通过，会获得一个认证管理器对象
         * 其中认证的动作是先根据username获取userDetails对象，然后通过认证管理器去认证用户输入的信息与userDetails信息是否匹配
         * 在UserDetailServiceImpl中实现获取userDetails对象,可以查看源码了解ProviderManager调用UserDetailServiceImpl.loadUserByUsername的过程
         * authenticationManager是在AuthorizationServerConfig中注册的一个bean,这里可以直接注入并使用其认证功能)
         **/
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (null == authentication) {
            throw new Appexception(AppexcepitonCodeMsg.SYSTEM_ERROR);
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
            throw new Appexception(AppexcepitonCodeMsg.REDIS_CONNECTION_ERROR);
        }
        //使用username生成token,存入loginResultDo对象中返回请求方，作为下一次请求的凭证
        String token = JWTUtil.createJWT(loginUserVo.getUsername());
        return Resp.success(new LoginResultDo(token));
    }

    @Override
    public Resp<String> logout() {

        //从security上下文中获取通过JWTFilter过滤的鉴权信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) authentication.getPrincipal();
        //清空redis中的用户信息
        stringRedisTemplate.delete(loginUserVo.getUsername());
        return Resp.success();
    }





}
