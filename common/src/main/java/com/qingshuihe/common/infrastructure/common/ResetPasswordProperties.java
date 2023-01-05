package com.qingshuihe.common.infrastructure.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/1/5
 **/
@Component
@ConfigurationProperties(prefix = "resetpw")
@Data
public class ResetPasswordProperties {
    /**
     * @Description: 重置密码邮件标题
     * @Date: 2023/1/5
     **/
    private String subject;
    /**
     * @Description: 重置密码邮件链接正文
     * @Date: 2023/1/5
     **/
    private String html;
    /**
     * @Description: 重置密码链接有效期
     * @Date: 2023/1/5
     **/
    private int vaildTime;
    /**
     * @Description: 重置密码邮件正文
     * @Date: 2023/1/5
     **/
    private String text;
    /**
     * @Description: 重置密码前端页面链接
     * @Date: 2023/1/5
     **/
    private String frontPage;
}
