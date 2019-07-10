package com.example.spring.oauth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 1.配置认证服务器AuthorizationServer
 *      client的注册到授权务器信息
 *              clientId
 *              secret
 *              authorizationType
 *              scopes
 *              redirectUris
 *
 * 2.配置认证服务器WebSecurityConfigurerAdapter
 *         验证username + password
 *
 * 3.获取授权码：http://localhost:8080/oauth/authorize?client_id=client&response_type=code
 * 4.第一次页面
 * 5.点击approve
 * 6.回调http://www.funtl.com/?code=1JuO6V 返回code
 * 7.http://client:secret@localhost:8080/oauth/token
 *      参数  grant_type authorization_code
 *           code = 1JuO6V
 *      返回：{
 *              "access_token": "140dedd2-a163-4b7f-b5b6-b9dc922b40b3",
 *              "token_type": "bearer",
 *              "expires_in": 43199,
 *              "scope": "app"
 *           }
 *
 *
 */
@Configuration
@EnableAuthorizationServer//认证服务器
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth.clientId}")
    private String clientId;
    @Value("${oauth.secret}")
    private String secret;
    @Value("${oauth.authorizedGrantTypes}")
    private String authorizedGrantTypes;
    @Value("${oauth.scopes}")
    private String scopes;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //基于内存存储令牌
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(clientId) //客户端Id
                .secret(passwordEncoder.encode(secret)) //密钥
                .authorizedGrantTypes(authorizedGrantTypes) //授权模式
                .scopes(scopes) //授权范围
                .redirectUris("https:www.baidu.com");
    }
}
