package com.exampl.spring.oauth2.social;

import java.lang.invoke.MethodHandles;

/**
 *      ServiceProvider
 *
 *    OAuth2ServiceProvider
 *
 *  AbstractOAuth2ServiceProvider -> OAuth2Operations                    ApiBinding
 *
 *  GenericOAuth2ServiceProvider     OAuth2Template  ->AccessGrant  | AbstractOAuth2ApiBinding（每个服务提供商返回的用户信息都是有差别的）
 *
 *                                                                                   Connection
 *
 *                                                                                   AbstractConnection
 *
 *  OAuth2ConnectionFactory(OAuth2ConnectionFactory#createConnection(AccessGrant)) ->OAuth2Connection
 *
 *  GenericOAuth2ConnectionFactory(new GenericOAuth2ServiceProvider注入) （ServiceProvider<A> serviceProvider, ApiAdapter<A> apiAdapter）
 *
 *
 *  UsersConnectionRepository<----(Connection)
 *
 *  SocialConfigurerAdapter
 *  SocialAutoConfigurerAdapter(核心注入) -->注入connectionFactory
 *
 *
 * @EnableSocial
 * SecurityConfigurerAdapter
 * SpringSocialConfigurer  -->SocialAuthenticationFilter //自动根据请求的url进行拦截providerId选取对应的第三方框架
 *
 * //总结：4，6，3
 *
 *
 *  //social过滤器
 *  SocialAuthenticationFilter
 *
 *  SocialAuthenticationToken
 *
 */

/**
 *
 * TokenEndpoint
 *
 * ConsumerTokenServices --> DefaultTokenServices
 *
 * TokenStore
 *
 * TokenEnhancerChain
 *      TokenEnhancer               ---> jwt 中添加额外的用户信息
 *      JwtAccessTokenConverter     ---> jwt 增强额外信息
 *
 * TokenGranter
 *      AbstractTokenGranter
 *
 *      OAuth2Authentication
 *      OAuth2RequestFactory
 * TokenRequest -->  OAuth2Request --> OAuth2Authentication (父 AbstractAuthenticationToken)
 * AuthorizationRequest
 *
 *
 *      CompositeTokenGranter
 *
 *
 *  tokenValue --> OAuth2AccessToken  --> OAuth2Authentication
 *
 *  // 逆向操作
 *  OAuth2AccessToken accessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
 *
 *
 *  UserDetailsService
 *
 *  ClientDetailsServiceImpl
 *
 *  AuthenticationManager
 *
 *
 *  SecurityConfigurerAdapter
 *
 *  AbstractAuthenticationProcessingFilter
 *      AuthenticationManager
 *          AuthenticationProvider(DaoAuthenticationProvider) | SmsAuthenticationProvider(自定义)
 *
 */

public class SocialConfig {

    public static void main(String[] args) {
        System.out.println(MethodHandles.lookup().lookupClass());
    }


}
