package com.example.spring.email;

//import org.apache.velocity.app.VelocityEngine;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EmailApplication.class)
//@SpringApplicationConfiguration(classes = EmailApplication.class)
public class EmailApplicationTest {

    @Autowired
    private JavaMailSender javaMailSender;
    //@Autowired
    //private VelocityEngine velocityEngine;

    @Test
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        //spring.mail.username=1245007716@qq.com
        //spring.mail.password=vntmiogqxtpajcid  对应
        message.setFrom("1245007716@qq.com");
        message.setTo("1245007716@qq.com");
        message.setSubject("主题：测试邮件");
        message.setText("测试邮件功能");
        javaMailSender.send(message);
    }

    @Test
    public void sendEmail1() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("1245007716@qq.com");
        helper.setTo("1245007716@qq.com");
        helper.setSubject("主题：测试邮件");
        //helper.setText("<html><body><img src=\"cid:RocketMQ的事务消息.png\" ></body></html>", true);

        Map<String, Object> model = new HashMap();
        model.put("username", "didi");
        //String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "template.vm", "UTF-8", model);
        //helper.setText(text, true);

        FileSystemResource file = new FileSystemResource(new File("E:\\myStudy\\image\\RocketMQ的事务消息.png"));
        helper.addAttachment("附件1.png", file);
        javaMailSender.send(mimeMessage);
    }
}
