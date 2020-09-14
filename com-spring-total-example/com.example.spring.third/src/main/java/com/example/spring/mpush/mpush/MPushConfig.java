package com.example.spring.mpush.mpush;

import com.mpush.api.MPushContext;
import com.mpush.api.push.PushSender;
import com.mpush.api.spi.common.ServiceDiscoveryFactory;
import com.mpush.api.srd.ServiceDiscovery;
import com.mpush.client.MPushClient;
import com.mpush.client.push.PushClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MPushConfig {
    @Bean(name = {"serviceDiscovery"})
    public ServiceDiscovery getZKServiceRegistryAndDiscoveryBean() {
        ServiceDiscovery serviceDiscovery = ServiceDiscoveryFactory.create();
        return serviceDiscovery;
    }

    @Bean(name = {"mPushClient"})
    public MPushClient getMPushClientBean() {
        MPushClient mPushClient = new MPushClient();
        return mPushClient;
    }

    @Bean(name = {"pushSender"})
    public PushSender getPushSenderBean(MPushClient mPushClient) {
        PushClientFactory factory = new PushClientFactory();
        PushSender sender = factory.get();
        sender.setMPushContext((MPushContext)mPushClient);
        sender.start();
        return sender;
    }
}
