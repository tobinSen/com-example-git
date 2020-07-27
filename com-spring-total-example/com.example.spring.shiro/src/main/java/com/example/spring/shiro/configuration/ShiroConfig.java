package com.example.spring.shiro.configuration;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 1、ShiroFilter
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap  = new HashMap<>();
        filterChainDefinitionMap.put("/login.do", "anno"); //authc是过滤器简称
        filterChainDefinitionMap.put("/**", "authc"); //authc是过滤器简称
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    //2、DefaultWebSecurityManager

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        defaultWebSecurityManager.setCacheManager(new RedisCacheManager());
//        defaultWebSecurityManager.setSessionManager();
//        defaultWebSecurityManager.setRememberMeManager();
//        defaultWebSecurityManager.setSubjectDAO();
        return defaultWebSecurityManager;
    }

    //3、设置自定义Realm
    @Bean
    public Realm realm() {
        CustomerAuthorizingRealm customerAuthorizingRealm = new CustomerAuthorizingRealm();

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME); //算法md5
        matcher.setHashIterations(1024); //hash散列次数

        customerAuthorizingRealm.setCredentialsMatcher(matcher);

        return customerAuthorizingRealm;
    }

}
