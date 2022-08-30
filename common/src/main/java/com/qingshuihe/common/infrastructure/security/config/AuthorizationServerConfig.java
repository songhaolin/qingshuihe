package com.qingshuihe.common.infrastructure.security.config;


import com.google.common.base.Predicates;
import com.qingshuihe.common.utils.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Documentation;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


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
 * /**
 *
 * @Description: swagger配置
 * @Date: 2022/8/29
 * @Param null:
 **/
@Configuration//加注解，使得加载容器时能扫描到该配置文件
public class AuthorizationServerConfig {
    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("webApi")
                .apiInfo(webApiInfo()).select().paths(Predicates.not(PathSelectors.regex("/error*")))
                .build().globalOperationParameters(getParameterList());
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder().title("Api中心")
                .description("从零学习springbott之swagger2")
                .version("1.0")
                .build();
    }

    /**
     * @Description: 获取请求参数列表, 封装到list中，便于上面的两个方法使用
     * @Date: 2022/8/29
     **/
    private List<Parameter> getParameterList() {
        ParameterBuilder clientIdTickt = new ParameterBuilder();
        ArrayList<Parameter> parameters = new ArrayList<>();
        //该行是为了检查请求头中的token信息，为了后续做token验证，”false“表示token是非必须的
        clientIdTickt.name(CommonConstant.TOKEN_STR).description("请求令牌").
                modelRef(new ModelRef("String")).parameterType("header").required(false).build();
        parameters.add(clientIdTickt.build());
        return parameters;
    }
}
