package com.qingshuihe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Description:
 * @Author: shl
 * @Date: 2022/8/29
 **/
@SpringBootApplication
@MapperScan("com.qingshuihe.common.domain.service.user.mapper")
@MapperScan("com.qingshuihe.common.domain.service.role.mapper")
@MapperScan("com.qingshuihe.common.domain.service.attach.mapper")
@EnableSwagger2
public class CommonApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CommonApp.class);
        System.out.println("begin start application=====================");
    }
}
