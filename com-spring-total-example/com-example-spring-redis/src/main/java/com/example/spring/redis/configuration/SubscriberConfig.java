package com.example.spring.redis.configuration;

import com.example.spring.redis.consumer.RedisMqConsumer;
import com.example.spring.redis.consumer.RedisMqConsumerSub;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class SubscriberConfig {

    /**
     * 消息监听适配器，注入接受消息方法，输入方法名字 反射方法
     *
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter getMessageListenerAdapter() {
        return new MessageListenerAdapter(redisMqConsumer(), "onMessage"); //当没有继承MessageListener时需要写方法名字
    }

    @Bean("redisMqConsumer")
    public RedisMqConsumer redisMqConsumer() {
        return new RedisMqConsumer();
    }

    @Bean("redisMqConsumerSub")
    public RedisMqConsumerSub redisMqConsumerSub() {
        return new RedisMqConsumerSub();
    }

    /**
     * 方式一：
     * 创建消息监听容器
     */
//    @Bean
//    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
//                                                                          @Qualifier("redisMqConsumer") MessageListener messageListener,
//                                                                          @Qualifier("redisMqConsumerSub") MessageListener messageListenerSub) {
//        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//        redisMessageListenerContainer.addMessageListener(messageListener, new PatternTopic("TOPIC_USERNAME"));
//        redisMessageListenerContainer.addMessageListener(messageListenerSub, new PatternTopic("TOPIC_USERNAME"));
//        return redisMessageListenerContainer;
//    }

    //方式二；
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                          MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        // redisMessageListenerContainer.addMessageListener(messageListener, new PatternTopic("TOPIC_USERNAME"));
        //redisMessageListenerContainer.addMessageListener(messageListenerSub, new PatternTopic("TOPIC_USERNAME"));
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("TOPIC_USERNAME"));
        return redisMessageListenerContainer;
    }
}
