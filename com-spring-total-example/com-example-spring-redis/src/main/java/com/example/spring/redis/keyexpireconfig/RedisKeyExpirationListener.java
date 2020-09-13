package com.example.spring.redis.keyexpireconfig;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

/**
 * 监听所有db的过期事件__keyevent@*__:expired"
 * <p>
 * <p>
 * KeyExpirationEventMessageListener
 * KeyspaceEventMessageListener
 * <p>
 * 总结：6点
 */

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    public RedisKeyExpirationListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        super(redisMessageListenerContainer);
    }

    /**
     * 针对redis数据失效事件，进行数据处理
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 用户做自己的业务处理即可,注意message.toString()可以获取失效的key
        String expiredKey = message.toString();
        if (expiredKey.startsWith("Order:")) {
            //如果是Order:开头的key，进行处理
        }
    }

}
