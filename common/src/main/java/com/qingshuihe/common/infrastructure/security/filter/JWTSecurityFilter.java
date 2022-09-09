package com.qingshuihe.common.infrastructure.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
import com.qingshuihe.common.utils.CommonConstant;
import com.qingshuihe.common.utils.JWTUtil;
import com.qingshuihe.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: JWTtoken控制过滤器，需要将这个过滤器加在（security用户名密码、权限过滤器）之前，这样可以避免掉一部分带有token请求的权限认证
 * 添加token解析过滤器在用户名密码过滤器之前的动作需要AuthorizationServerConfig的configure中配置，
 * 使用语法为：" .and().addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)"
 * OncePerRequestFilter顾名思义，它能够确保在一次请求中只通过一次filter，而不会重复执行
 * @Author: shl
 * @Date: 2022/9/1
 **/
@Component
public class JWTSecurityFilter extends OncePerRequestFilter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        checkToken(httpServletRequest,httpServletResponse,filterChain);
    }

    private void checkToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader(CommonConstant.TOKEN_STR);
//        String password = passwordEncoder.encode(CommonConstant.PASS_WORD);
        if (StringUtils.isNotEmpty(token)){
            String username = JWTUtil.parseJWT(token).getSubject();
            //根据token中解析出的username从缓存中获取userdetails信息，如果获取到redis信息，则将用户信息存入上下文中,并跳过后续的
            String userInfo = stringRedisTemplate.opsForValue().get(username);
            if (StringUtils.isNotEmpty(userInfo)){
                LoginUserVo loginUserVo = JSONObject.parseObject(userInfo,LoginUserVo.class);
                /**
                 * 这里将从token中获取的用户信息加入到认证管理器上下文中，这样就直接获取到了权限，不用再次通过认证了
                 * 新建的用户名密码权限管理器，将上面从redis中获取的权限信息直接塞进管理器，加入认证管理器上下文中即可
                 **/
                //
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserVo, null, loginUserVo.getAuthorities());
                //将用户信息放入security的上下文根中，不用再做权限认证了
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        doFilter(httpServletRequest,httpServletResponse,filterChain);
    }
}
