package com.exampl.spring.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 授权码模式：
 *  1.http://localhost:9999/oauth/authorize?client_id=client&response_type=code
 *  2.在回调的接口：http://www.baidu.com?code=QHU15v 获取code
 *  3.http://localhost:8001/oauth/token?client_id=client&client_secret=123456&grant_type=authorization_code&redirect_uri=http://www.baidu.com&code=AKAQUe
 *  4.返回access_token 和refresh_token
 *
 *  注意：第一次获取code的时候，会跳转到登录页面(重点)
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client") // 客户端ID
                .scopes("app") // 允许的授权范围
                .autoApprove(true)  // 如果为true　则不会跳转到授权页面，而是直接同意授权返回code
                .authorizedGrantTypes("authorization_code", "refresh_token") // 设置验证方式
                .redirectUris("http://localhost:9999/callback") //回调地址，也可以配置文件中定义
                .secret(new BCryptPasswordEncoder().encode("123456"))   //必须加密
                .accessTokenValiditySeconds(10000) //token过期时间
                .refreshTokenValiditySeconds(10000); //refresh过期时间;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userService); //配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
    }

    @Bean
    public TokenStore tokenStore() {
        // 使用内存的tokenStore
        return new InMemoryTokenStore();
    }

}
