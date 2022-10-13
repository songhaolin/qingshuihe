package com.qingshuihe.common.infrastructure.aspect;

import com.alibaba.fastjson.JSONObject;
import com.qingshuihe.common.domain.log.entity.LogEntity;
import com.qingshuihe.common.infrastructure.mq.ProductMessageHandler;
import com.qingshuihe.common.interfaces.outbond.admin.vo.LoginUserVo;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description:日志切面类，用来做@LogAnnotation注解生效时的实际处理
 *
 * @Aspect:作用是把当前类标识为一个切面供容器读取
 *
 * @Pointcut：Pointcut是植入Advice的触发条件。每个Pointcut的定义包括2部分，一是表达式，二是方法签名。
 * 方法签名必须是public及void型。可以将Pointcut中的方法看作是一个被Advice引用的助记符，因为表达式不直观，
 * 因此我们可以通过方法签名的方式为 此表达式命名。因此Pointcut中的方法只需要方法签名，而不需要在方法体内编写实际代码。
 *
 * @Around：环绕增强，相当于MethodInterceptor
 * @AfterReturning：后置增强，相当于AfterReturningAdvice，方法正常退出时执行
 * @Before：标识一个前置增强方法，相当于BeforeAdvice的功能，相似功能的还有
 * @AfterThrowing：异常抛出增强，相当于ThrowsAdvice
 * @After: final增强，不管是抛出异常或者正常退出都会执行
 *
 *
 * @Author: shl
 * @Date: 2022/9/21
 **/
@Component
//声明该类是一个切面类，用来实现某个自定义注解
@Aspect
public class LogAspect {

    private int MAX_LENGTH = 500;
    private String ANONYMOUS_USER="anonymousUser";//匿名用户

    @Autowired
    private ProductMessageHandler productMessageHandler;

    /**
     * @Description: 表示该切面的作用时间
     * @annotation(com.qingshuihe.common.infrastructure.aspect.LogAnnotation)表示在当前注解生效时执行该切面
     * @Date: 2022/9/21

     **/
    @Pointcut(value = "@annotation(com.qingshuihe.common.infrastructure.aspect.LogAnnotation)")
    public void annotationPointCut() {}

    @Around("annotationPointCut()")
    Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取当前方法传过来的实现类的接口,获取接口类
        Class<?>[] class_ = joinPoint.getTarget().getClass().getInterfaces();

        //获取当前方法传过来的方法名
        String methodName = joinPoint.getSignature().getName();
        //获取当前方法传过来的参数
        Object[] arguments = joinPoint.getArgs();

        //获取当前方法传过来的类
        Class<?> classTarget = joinPoint.getTarget().getClass();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        //获取当前方法传过来的方法，具体的方法，不只是方法名
        Method objMethod = classTarget.getMethod(methodName, parameterTypes);
        objMethod = class_[0].getMethod(methodName,parameterTypes);
        //通过获取的当前方法，获取当前方法上的注解；这里也可以通过接口去获取，
        //这里拿到ApiOperation注解，就可以拿到@ApiOperation描述的内容，value和notes都可以，
        //例如@ApiOperation(value="/admin/login",notes="用户登录")
        //这里也可以获取controller层的PostMapping上的接口路径和描述，使用对应的方法即可
        ApiOperation apiOperation = objMethod.getAnnotation(ApiOperation.class);

        //根据获取到的信息，构建日志实体，并通过生产者发送到mq中
        LogEntity logEntity = new LogEntity();
        logEntity.setOperation(apiOperation.value());
        logEntity.setDescription(apiOperation.notes());
        String params = JSONObject.toJSONString(arguments);
        //真实业务系统中，应当对这里记录的参数做脱敏处理
        //防止数据库放不下，对参数长度进行处理
        params = params.length()>MAX_LENGTH?params.substring(0,MAX_LENGTH-3)+"...":params;
        logEntity.setParam(params);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().toString().contains(ANONYMOUS_USER)){
            logEntity.setCreateBy("visitor");
        }else {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            LoginUserVo loginUserVo = (LoginUserVo) usernamePasswordAuthenticationToken.getPrincipal();
            logEntity.setCreateBy(String.valueOf(loginUserVo.getId()));
        }
        productMessageHandler.sendLog(logEntity);
        return joinPoint.proceed();
    }
}
