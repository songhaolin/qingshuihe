package com.qingshuihe.common.infrastructure.security.config;


import com.qingshuihe.common.infrastructure.security.filter.JWTSecurityFilter;
import com.qingshuihe.common.utils.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


/**
 * Swagger2是什么？
 * Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。
 * <p>
 * 2.Swagger2的特点是什么？
 * （1） 及时性 (接口变更后，能够及时准确地通知相关前后端开发人员)
 * （2）规范性 (并且保证接口的规范性，如接口的地址，请求方式，参数及响应格式和错误信息)
 * （3）一致性 (接口信息一致，不会出现因开发人员拿到的文档版本不一致，而出现分歧)
 * （4）可测性 (直接在接口文档上进行测试，以方便理解业务
 * /
 * <p>
 * 该配置文件将swagger+security结合，将请求通过security过滤拦截
 * <p>
 * /**
 *
 * @Description: swagger配置
 * @Date: 2022/8/29
 * @Param null:
 **/
@Configuration//加注解，使得加载容器时能扫描到该配置文件，并且可以加载bean实例
//认证配置文件需要继承WebSecurityConfigurerAdapter才能使用security的一套鉴权体系
//加注解用来开启全局的鉴权配置，配合接口入口出的注解@PreAuthorize使用，用来鉴定请求用户是否具备所需接口的请求权限
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPointImpl;
    @Autowired
    private AccessDeniedHandler accessDeniedHandlerImpl;

    @Autowired
    private JWTSecurityFilter jwtSecurityFilter;

    /**
     * @Description: 配置security的请求过滤、拦截；用户认证、鉴权等功能
     * @Date: 2022/8/31
     * @Param http:
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        //配置对请求的拦截策略。需要指定哪些用户可以访问哪些接口
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/*", "/webjars/**", "/swagger-resources/**", "/v2/**", "/admin/login","/admin/sendResetLink","/admin/sendVerifyCode").anonymous()
//                .antMatchers("/admin/**").hasRole("admin") 可以在这里从更大的面设置对于接口的权限限制，方法上的注解更为精细
                .anyRequest()
                .authenticated()
                //添加token解析过滤器在用户名密码过滤器之前，可以直接解析带token的请求并获取其权限信息，不用再做权限认证
                .and().addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                //添加认证失败或者权限不够的异常处理过滤器，需要自定义实现这两个过滤器，然后在配置是使用@Autowired注入进来
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl).accessDeniedHandler(accessDeniedHandlerImpl);
    }

    /**
     * @Description: 注册认证管理器，结合
     * @Date: 2022/8/31
     **/
    @Bean
    @Override
    //
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * @Description: 注册security密码加密工具实例，
     * 作为加解密的算法，用以对用户输入的密码做加解密处理，并与数据库信息对比。
     * 该实例会在认证管理器做认证时对用户输入的密码做加密处理并与数据库信息对比
     * @Date: 2022/8/31
     **/
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //****************************************swagger2.7.0版本的配置，不支持在页面做附件上传下载功能************************

    /**
     * @Description: 注册swagger的实例，配置swagger的相关内容
     * @Date: 2022/8/31
     **/
//    @Bean
//    public Docket webApiConfig() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi")
//                .apiInfo(webApiInfo()).select().paths(Predicates.not(PathSelectors.regex("/error*")))
//                .build().globalOperationParameters(getParameterList());
//    }
//
//    /**
//     * @Description: 设置swagger的api文档页面的相关内容
//     * @Date: 2022/8/31
//     **/
//    private ApiInfo webApiInfo() {
//        return new ApiInfoBuilder().title("Api中心")
//                .description("从零学习springbott之swagger2")
//                .version("1.0")
//                .build();
//    }
//
//    /**
//     * @Description: 获取请求参数列表, 封装到list中，便于上面的两个方法使用
//     * @Date: 2022/8/29
//     **/
//    private List<Parameter> getParameterList() {
//        ParameterBuilder clientIdTickt = new ParameterBuilder();
//        ArrayList<Parameter> parameters = new ArrayList<>();
//        //该行是为了检查请求头中的token信息，为了后续做token验证，”false“表示token是非必须的
//        clientIdTickt.name(CommonConstant.TOKEN_STR).description("请求令牌").
//                modelRef(new ModelRef("String")).parameterType("header").required(false).build();
//        parameters.add(clientIdTickt.build());
//        return parameters;
//    }

    //****************************************升级swagger到3.0.0版本，支持在页面做附件上传下载功能************************

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi").apiInfo(webApiInfo()).select().paths(PathSelectors.regex("/error").negate()).build().globalRequestParameters(getParameterList());
    }
    /**
    * @Description: 设置swagger的api文档页面的相关内容
    * @Date: 2022/8/31
    **/
    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder().title("API中心").description("spring boot 从零开始").version("1.0").build();
    }
    /**
      * @Description: 获取请求参数列表, 封装到list中，便于上面的两个方法使用
      * @Date: 2022/9/15
      **/
    private List<RequestParameter> getParameterList(){
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name(CommonConstant.TOKEN_STR)
                .description("请求令牌")
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }
}
