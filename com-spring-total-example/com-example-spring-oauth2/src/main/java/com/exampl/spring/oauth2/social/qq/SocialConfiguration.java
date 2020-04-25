//package com.exampl.spring.oauth2.social.qq;
//
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.encrypt.Encryptors;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurerAdapter;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
//import org.springframework.social.connect.web.ProviderSignInUtils;
//import org.springframework.social.security.AuthenticationNameUserIdSource;
//import org.springframework.social.security.SocialUserDetailsService;
//
///**
// * social 配置
// *
// */
//@Configuration
//@EnableSocial
//@EnableConfigurationProperties(SocialProperties.class)
//public class SocialConfiguration extends SocialConfigurerAdapter {
//
//    @Autowired
//    private SocialProperties properties;
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired(required = false)
//    private ConnectionSignUp connectionSignUp;
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
//        // QQ
//        SocialProperties.Qq qq = properties.getQq();
//        if (StringUtils.isNoneBlank(qq.getAppId(), qq.getAppSecret())) {
//            connectionFactoryConfigurer.addConnectionFactory(
//                    new QQConnectionFactory(qq.getProviderId(), qq.getAppId(), qq.getAppSecret()));
//        }
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        JdbcUsersConnectionRepository usersConnectionRepository =
//                new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
//        // 设置表前缀
//        usersConnectionRepository.setTablePrefix("sys_");
//        // ConnectionSignUp 需自定义
//        usersConnectionRepository.setConnectionSignUp(connectionSignUp);
//        return usersConnectionRepository;
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
//        return new AuthenticationNameUserIdSource();
//    }
//
//    @Bean
//    public SocialUserDetailsService socialUserDetailsService() {
//        return new CustomSocialUserDetailsService();
//    }
//
//    @Bean
//    public CustomSocialAuthenticationSuccessHandler socialAuthenticationSuccessHandler() {
//        return new CustomSocialAuthenticationSuccessHandler();
//    }
//
//    //@Bean
//    //public CustomSocialAuthenticationFailureHandler customSocialAuthenticationFailureHandler() {
//    //    return new CustomSocialAuthenticationFailureHandler();
//    //}
//
//    @Bean
//    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator,
//                                                   UsersConnectionRepository connectionRepository) {
//        return new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
//    }
//
//}
