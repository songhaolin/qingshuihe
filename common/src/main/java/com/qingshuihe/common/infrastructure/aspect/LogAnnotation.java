package com.qingshuihe.common.infrastructure.aspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 自定义注解，配合切面方法，用来统一处理日志记录
 * @Date: 2022/9/21
 * 当系统运行时，感知到当前注解后，需要处理的方法为LogAspect类中的方法
 **/
//@Target表示，该注解作用范围是方法之上
@Target(value = {ElementType.METHOD})
//@Retention表示，该注解作用时间是运行时
@Retention(RetentionPolicy.RUNTIME)
public @interface  LogAnnotation {
    String value() default "";
}
