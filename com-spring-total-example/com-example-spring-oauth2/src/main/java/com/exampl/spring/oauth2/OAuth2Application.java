package com.exampl.spring.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.wechat.autoconfigurer.WechatMpAutoConfiguration;

@SpringBootApplication
@EnableSocial
@Import(WechatMpAutoConfiguration.class)
public class OAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(OAuth2Application.class, args);
    }
}
