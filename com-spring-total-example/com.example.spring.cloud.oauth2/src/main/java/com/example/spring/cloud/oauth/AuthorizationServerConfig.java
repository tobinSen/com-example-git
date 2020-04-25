package com.example.spring.cloud.oauth;

import org.omg.CORBA.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private LoginService loginService;

    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    @Autowired
    private Environment env;


    @Autowired
    private DataSource dataSource;

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(jwtTokenEnhancer); //配置JWT的内容增强器
        delegates.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)//支持 password 模式
                //.userDetailsService(loginService)
                .tokenStore(tokenStore) //配置令牌存储策略
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(enhancerChain);
    }

    /**
     * redis token 方式
     */
    /*@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //支持 password 模式
                .userDetailsService(loginService)
                .tokenStore(tokenStore);

    }*/

    /**
     * 客户端配置（给谁发令牌）
     * 不同客户端配置不同
     * <p>
     * authorizedGrantTypes 可以包括如下几种设置中的一种或多种：
     * authorization_code：授权码类型。需要redirect_uri
     * implicit：隐式授权类型。需要redirect_uri
     * password：资源所有者（即用户）密码类型。
     * client_credentials：客户端凭据（客户端ID以及Key）类型。
     * refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
     * <p>
     * accessTokenValiditySeconds：token 的有效期
     * scopes：用来限制客户端访问的权限，在换取的 token 的时候会带上 scope 参数，只有在 scopes 定义内的，才可以正常换取 token。
     *
     * @param clients
     * @throws Exception
     * @author Damon
     * @date 2020年1月13日
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin-web")
                .secret(passwordEncoder.encode("admin-web-123"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)//配置刷新token的有效期
                .autoApprove(true) //自动授权配置
                .scopes("all")//配置申请的权限范围
                .authorizedGrantTypes("password", "authorization_code", "client_credentials", "refresh_token")//配置授权模式
                .redirectUris("http://localhost:2001/login")//授权码模式开启后必须指定
                .and()
                .withClient("order-service")
                .secret(passwordEncoder.encode("order-service-123"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)//配置刷新token的有效期
                .autoApprove(true) //自动授权配置
                .scopes("all")
                .authorizedGrantTypes("password", "authorization_code", "client_credentials", "refresh_token")//配置授权模式
                .redirectUris("http://localhost:2003/login")//授权码模式开启后必须指定
        //.authorizedGrantTypes("authorization_code", "password", "refresh_token")//配置授权模式：authorization_code授权码模式、密码模式
        ;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();//是允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401
        security.checkTokenAccess("isAuthenticated()");//是允许已授权用户访问 checkToken 接口
        security.tokenKeyAccess("isAuthenticated()"); // security.tokenKeyAccess("permitAll()");获取密钥需要身份认证，使用单点登录时必须配置，是允许已授权用户获取 token 接口
    }
}

