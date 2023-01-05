package com.qingshuihe.common.infrastructure.mail;

import com.qingshuihe.common.infrastructure.mail.vo.MyMailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @Description:
 * @Author: shl
 * @Date: 2023/1/5
 **/
@Component
public class EmailHandler {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private MailProperties mailProperties;

    /**
     * @Description: 发送无附件的简单邮件
     * @Date: 2023/1/5
     * @Param myMailVo:
     **/
    public boolean sendSimpleMail(MyMailVo myMailVo) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setText(myMailVo.getText());
        simpleMailMessage.setSubject(myMailVo.getSubject());
        simpleMailMessage.setFrom(mailProperties.getUsername());
        if (null != myMailVo.getTo()) {
            simpleMailMessage.setTo(myMailVo.getTo());
        }
        if (null != myMailVo.getCc()) {
            simpleMailMessage.setCc(myMailVo.getCc());
        }
        javaMailSender.send(simpleMailMessage);
        return true;
    }
    /**
     * @Description:发送带多附件的复杂邮件
     * @Date: 2023/1/5
     * @Param myMailVo: 邮件类
     * @Param isHaveMultipart: 是否有附件
     **/
    public boolean sendMimeMail(MyMailVo myMailVo, Boolean isHaveMultipart) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setSubject(myMailVo.getSubject());
            mimeMessageHelper.setText(myMailVo.getText(), true);
            if (null != myMailVo.getTo()) {
                mimeMessageHelper.setTo(myMailVo.getTo());
            }
            if (null != myMailVo.getCc()) {
                mimeMessageHelper.setCc(myMailVo.getCc());
            }
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            if (isHaveMultipart) {
                for (File file : myMailVo.getFiles()) {
                    mimeMessageHelper.addAttachment(file.getName(), file);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
        return true;
    }

}
