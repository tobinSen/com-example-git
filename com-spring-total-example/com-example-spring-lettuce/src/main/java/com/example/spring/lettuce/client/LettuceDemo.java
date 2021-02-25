package com.example.spring.lettuce.client;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LettuceDemo {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        //1.创建redisURI
        RedisURI redisURI = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();

        //2.创建redisClient
        RedisClient redisClient = RedisClient.create(redisURI);
        //3.创建链接
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        //4.同步的命令
        RedisCommands<String, String> syncCommands = connect.sync();


        StatefulRedisPubSubConnection<String, String> pubSub = redisClient.connectPubSub();
        RedisPubSubCommands<String, String> commands = pubSub.sync();
        // 只接收键过期的事件
        commands.configSet("notify-keyspace-events", "Kx");
        pubSub.addListener(new RedisPubSubListener<String, String>() {
            @Override
            public void message(String channel, String message) {
                System.out.println("channel:" + channel + "message:" + message);
            }

            @Override
            public void message(String pattern, String channel, String message) {
                System.out.println("pattern" + pattern + "channel:" + channel + "message:" + message);
            }

            @Override
            public void subscribed(String channel, long count) {
                System.out.println("channel:" + channel + "channel:" + count);
            }

            @Override
            public void psubscribed(String pattern, long count) {
                System.out.println("pattern:" + pattern + "channel:" + count);
            }

            @Override
            public void unsubscribed(String channel, long count) {
                System.out.println("channel:" + channel + "channel:" + count);

            }

            @Override
            public void punsubscribed(String pattern, long count) {
                System.out.println("pattern:" + pattern + "channel:" + count);
            }
        });
        commands.psubscribe("__keyspace*__:cool:*");  // pSubscribe 支持通配符
        syncCommands.setex("cool:tongjian", 4, "val");

        syncCommands.set("tong", "val");

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        for (int i = 0; i < 10; i++) {
            System.err.println(syncCommands.get("tong"));
            Object val = syncCommands.eval(script, ScriptOutputType.INTEGER, new String[]{"tong"}, "val");
            System.err.println(syncCommands.get("tong"));
            System.err.println(val);

            TimeUnit.SECONDS.sleep(5);
        }

        latch.await();
    }
}
