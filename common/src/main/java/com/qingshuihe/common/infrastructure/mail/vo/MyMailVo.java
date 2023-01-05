package com.qingshuihe.common.infrastructure.mail.vo;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @Description: 自定义邮件类，可以选择继承extends SimpleMailMessage，需要对该父类的属性了解，这里选择不继承
 * @Author: shl
 * @Date: 2023/1/5
 **/
@Data
public class MyMailVo {
    /**
     * @Description: 主送人员地址
     * @Date: 2023/1/5
     **/
    private String[] to;
    /**
     * @Description: 抄送人员地址
     * @Date: 2023/1/5
     **/
    private String[] cc;
    /**
     * @Description: 密送人员地址
     * @Date: 2023/1/5
     **/
    private String[] bcc;
    /**
     * @Description: 邮件标题
     * @Date: 2023/1/5
     **/
    private String subject;
    /**
     * @Description: 邮件正文
     * @Date: 2023/1/5
     **/
    private String text;
    /**
     * @Description: 发送邮件附件
     * @Date: 2023/1/5
     **/
    private List<File> files;
    /**
     * @Description: 重写构造方法，支持单个和多个地址的设置
     * @Date: 2023/1/5
     * @Param to:
     **/
    public void setTo(String to) {
        this.to = new String[]{to};
    }

    public void setTo(String... to) {
        this.to = to;
    }

    public void setCc(String cc) {
        this.cc = new String[]{cc};
    }

    public void setCc(String... cc) {
        this.cc = cc;
    }

    public void setBcc(String bcc) {
        this.bcc = new String[]{bcc};
    }

    public void setBcc(String... bcc) {
        this.bcc = bcc;
    }
}
