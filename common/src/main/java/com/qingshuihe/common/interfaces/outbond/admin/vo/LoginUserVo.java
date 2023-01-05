package com.qingshuihe.common.interfaces.outbond.admin.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:登陆用户自定义spring security下的userDetails类，用来对请求用户做过滤、认证、鉴权
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Data
public class LoginUserVo implements UserDetails, Serializable {

    private static final long serialVersionUID = 646270045569046754L;

    private Long id;

    private UserVo userVo;

    private List<String> permissions;
    /**
     * @Description: 获取用户权限信息
     * @Date: 2022/8/31

     **/
    @Override
    @JSONField(serialize = false)//这个注解的作用是当前的实例与json对象互相转换时，不包含该属性。因为该属性为list<list>复杂结构，无法直接做json互转
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //这里是将格式为list<String>的权限集合通过lamada表达的方式直接转为格式为List<SimpleGrantedAuthority>格式的权限集合，用以鉴权
        return permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userVo.getPassword();
    }

    @Override
    public String getUsername() {
        return userVo.getUsername();
    }
    /**
     * @Description: 账号是否过期
     * true有效，false无效
     * @Date: 2022/8/31

     **/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @Description: 账号是否锁定
     * true未锁定，false锁定
     * @Date: 2022/8/31

     **/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @Description: 账号凭证是否过期
     * true有效，false无效
     * @Date: 2022/8/31

     **/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * @Description: 账号是否有效
     * true有效，false无效
     * @Date: 2022/8/31

     **/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
