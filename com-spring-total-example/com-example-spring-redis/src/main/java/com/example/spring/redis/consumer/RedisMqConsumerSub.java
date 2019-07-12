package com.example.spring.redis.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisMqConsumerSub implements MessageListener {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param message 消息体
     * @param bytes   topic
     *     注意：必须使用redis的序列化工具
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        RedisSerializer<String> defaultSerializer = stringRedisTemplate.getStringSerializer();
        String msg = defaultSerializer.deserialize(message.getBody());
        String channel = defaultSerializer.deserialize(message.getChannel());
        String pattern = defaultSerializer.deserialize(bytes);
        System.out.println("RedisMqConsumerSub接收到的消息为：" + msg + "--->" + pattern + "--->" + channel);
    }
}
