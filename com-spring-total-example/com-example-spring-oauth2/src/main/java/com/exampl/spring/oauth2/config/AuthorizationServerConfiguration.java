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
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 授权码模式：
 * 1.http://localhost:9999/oauth/authorize?client_id=client&response_type=code
 * 2.在回调的接口：http://www.baidu.com?code=QHU15v 获取code
 * 3.http://localhost:8001/oauth/token?client_id=client&client_secret=123456&grant_type=authorization_code&redirect_uri=http://www.baidu.com&code=AKAQUe
 * 4.返回access_token 和refresh_token
 * <p>
 * 注意：第一次获取code的时候，会跳转到登录页面(重点)
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userService; //这里是用户对应自己的项目信息

    @Autowired
    private TokenStore tokenStore;//access_token存储方式(jdbc redis jwt)

    @Autowired
    private ClientDetailsService clientDetailsService; //这里是当前项目注册到第三方登录系统的客户端信息

    //这里是token的信息
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);//设置客户端信息
        services.setSupportRefreshToken(true); //是否支持刷新token
        services.setTokenStore(tokenStore);   //存储access_token方式
        services.setAccessTokenValiditySeconds(60 * 60 * 2); //access_token的过期时间
        services.setRefreshTokenValiditySeconds(60 * 60 * 24 * 3); //refresh_token的过期时间
        return services;
    }

    //这里是code授权码
    @Bean
    public AuthorizationCodeServices codeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("permitAll()") //access_token会在资源服务器中去验证认证服务器auth, /oauth/check_token去调用认证服务器的token信息，这里是是否允许验证
                .allowFormAuthenticationForClients(); //允许表单验证
    }

    //客户端配置：你的应用注册到第三方服务器上，你的应用就是客户端配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("javaboy") //申请的
                .secret(new BCryptPasswordEncoder().encode("123"))
                .scopes("app") // 允许的授权范围
                .resourceIds("res1")

                .autoApprove(true)  // 如果为true　则不会跳转到授权页面，而是直接同意授权返回code
                .authorizedGrantTypes("authorization_code", "refresh_token") // 设置验证方式
                .redirectUris("http://localhost:9999/callback") //回调地址，也可以配置文件中定义
                .accessTokenValiditySeconds(10000) //token过期时间
                .refreshTokenValiditySeconds(10000); //refresh过期时间;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)//token
                .authorizationCodeServices(codeServices())//code
                .authenticationManager(authenticationManager)
                .userDetailsService(userService); //配置userService 这样每次认证的时候会去检验用户是否锁定，有效等
    }

    @Bean
    public TokenStore tokenStore() {
        // 使用内存的tokenStore
        return new InMemoryTokenStore();
    }

}
