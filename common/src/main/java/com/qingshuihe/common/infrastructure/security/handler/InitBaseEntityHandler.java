package com.qingshuihe.common.infrastructure.security.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.qingshuihe.common.interfaces.outbond.login.LoginUserVo;
import com.qingshuihe.common.utils.SnowFlake;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description: mybatis-plus自动数据填充处理器，通过实现MetaObjectHandler的方法来实现插入或者更新时自动更新对应的字段信息
 * 通过在实体类上加上@TableField(fill = FieldFill.INSERT )注解， 每次做相应的持久化操作时会自动的进行该处理器内的操作
 * @Author: shl
 * @Date: 2022/9/8
 **/
@Component
public class InitBaseEntityHandler implements MetaObjectHandler {

    @Autowired
    private SnowFlake snowFlake;

    @Override
    public void insertFill(MetaObject metaObject) {
        //当插入操作时，通过雪花算法获取当前插入的id
        long nextId = snowFlake.nextId();
//       这里必须使用setFiledValByName的方式，因为id有默认值为0，如果使用fillStratrgy的方式，将会无法更新id的值，反而会插入id为0的记录
        this.setFieldValByName("id",nextId,metaObject);
        //获取上下文根中的认证信息，将用户信息更新到createBy字段中
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) usernamePasswordAuthenticationToken.getPrincipal();
        this.fillStrategy(metaObject,"createBy",loginUserVo.getUsername());
        this.fillStrategy(metaObject,"createDate",new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUserVo loginUserVo = (LoginUserVo) usernamePasswordAuthenticationToken.getPrincipal();
        this.fillStrategy(metaObject,"updateBy",loginUserVo.getUsername());
        this.fillStrategy(metaObject,"updateDate",new Date());
    }
}
