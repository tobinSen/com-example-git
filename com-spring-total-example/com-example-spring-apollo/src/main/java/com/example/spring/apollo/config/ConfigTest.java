package com.example.spring.apollo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig
//@RefreshScope
public class ConfigTest {

    @Value("${java_apollo}")
    private String javaApollo;

    @ApolloConfig("application")
    private Config config1;

    public String getJavaApollo() {
        Config config= ConfigService.getAppConfig();
        String property = config.getProperty("java_apollo", "null");
        System.out.println(property);
        System.out.println("-------------");
        System.out.println(config1.getProperty("java_apollo",null ));
        return javaApollo;
    }
}
