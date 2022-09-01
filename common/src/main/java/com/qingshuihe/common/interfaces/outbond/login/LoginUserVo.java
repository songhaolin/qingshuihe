package com.qingshuihe.common.interfaces.outbond.login;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description:登陆用户自定义spring security下的userDetails类，用来对请求用户做过滤、认证、鉴权
 * @Author: shl
 * @Date: 2022/8/31
 **/
@Data
public class LoginUserVo implements UserDetails, Serializable {

    private static final long serialVersionUID = 646270045569046754L;

    private Integer id;

    private UserVo userVo;

    private List<String> permissions;
    /**
     * @Description: 获取用户权限信息
     * @Date: 2022/8/31

     **/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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