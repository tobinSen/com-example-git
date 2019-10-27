package com.example.spring.lettuce.configuartion;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RedisConfiguration {


    /**
     * 连接Redis
     * 单机、哨兵、集群模式下连接Redis需要一个统一的标准去表示连接的细节信息，在Lettuce中这个统一的标准是RedisURI。
     * 可以通过三种方式构造一个RedisURI实例：
     * <p>
     * 定制的字符串URI语法：
     * RedisURI uri = RedisURI.create("redis://localhost/");
     * 使用建造器（RedisURI.Builder）：
     * RedisURI uri = RedisURI.builder().withHost("localhost").withPort(6379).build();
     * 直接通过构造函数实例化：
     * RedisURI uri = new RedisURI("localhost", 6379, 60, TimeUnit.SECONDS);
     */

    @Test
    public void redisURI() {
        //1.创建单机连接的连接信
        RedisURI redisURI = RedisURI.builder().withHost("10.50.7.2")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS)).build();
        //2.创建客户端
        RedisClient redisClient = RedisClient.create(redisURI);
        //3.建立链接
        StatefulRedisConnection<String, String> connect = redisClient.connect();

        RedisCommands<String, String> redisCommands = connect.sync();

        redisCommands.set("key2", "value2", SetArgs.Builder.ex(5));

        String value2 = redisCommands.get("key2");
        System.out.println(value2);

        connect.close();
        redisClient.shutdown();

    }

    /**
     * API
     * Lettuce主要提供三种API：
     *
     * 同步（sync）：RedisCommands。
     * 异步（async）：RedisAsyncCommands。
     * 反应式（reactive）：RedisReactiveCommands
     */
}
