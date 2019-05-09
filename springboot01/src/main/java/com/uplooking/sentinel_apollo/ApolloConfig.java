package com.uplooking.sentinel_apollo;

import org.springframework.context.annotation.Configuration;

@Configuration

public
class

ApolloConfig {

    public static void main(String[] args) {
        System.out.println("---------->");
    }

    /*@Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }


    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }


    @Bean
    public ApolloOpenApiClient apolloOpenApiClient() {
        ApolloOpenApiClient client = ApolloOpenApiClient.newBuilder()
                .withPortalUrl("https://apollo.xxx.com") // TODO 根据实际情况修改
                .withToken("open api token") // TODO 根据实际情况修改
                .build();
        return client;
    }*/
}
