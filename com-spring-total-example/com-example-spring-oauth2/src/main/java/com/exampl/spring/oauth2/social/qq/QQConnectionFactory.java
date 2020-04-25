package com.exampl.spring.oauth2.social.qq;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * QQ Connection 工厂
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQApi> {


    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQApiAdapter());
    }
}
