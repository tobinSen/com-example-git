package com.example.spring.lettuce.configuartion;

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.Utf8StringCodec;
import io.lettuce.core.masterslave.MasterSlave;
import io.lettuce.core.masterslave.StatefulRedisMasterSlaveConnection;
import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import io.lettuce.core.pubsub.api.reactive.ChannelMessage;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Configuration
public class RedisConfiguration {


    /** 4,4,2,3点
     * 连接Redis
     * 单机、主从、哨兵、集群模式下连接Redis需要一个统一的标准去表示连接的细节信息，在Lettuce中这个统一的标准是RedisURI。
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
    public void redisURI() throws Exception {
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
        SetArgs setArgs = SetArgs.Builder.nx();
        String result = syncCommands.set("key1", "value1", setArgs);
        System.out.println(result);

        //异步插入数据
        RedisAsyncCommands<String, String> asyncCommands = connect.async();
        RedisFuture<String> redisFuture = asyncCommands.set("key1", "value1");
        redisFuture.thenAccept(System.out::println);
        String s = redisFuture.get();

        RedisReactiveCommands<String, String> reactiveCommands = connect.reactive();
        Mono<String> mono = reactiveCommands.set("key1", "value1");
        mono.subscribe(System.out::println);

        connect.close();
        redisClient.shutdown();




        /*发布订阅者模式*/
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        //注册监听器
        connection.addListener(new RedisPubSubAdapter<String, String>() {
            @Override
            public void message(String channel, String message) {
                super.message(channel, message);
            }

            @Override
            public void message(String pattern, String channel, String message) {
                super.message(pattern, channel, message);
            }

            @Override
            public void subscribed(String channel, long count) {
                super.subscribed(channel, count);
            }

            @Override
            public void psubscribed(String pattern, long count) {
                super.psubscribed(pattern, count);
            }

            @Override
            public void unsubscribed(String channel, long count) {
                super.unsubscribed(channel, count);
            }

            @Override
            public void punsubscribed(String pattern, long count) {
                super.punsubscribed(pattern, count);
            }
        });
        // 同步命令
        RedisPubSubCommands<String, String> sync = connection.sync();
        sync.subscribe("channel");

        // 异步命令
        RedisPubSubAsyncCommands<String, String> async = connection.async();
        RedisFuture<Void> future = async.subscribe("channel");

        // 反应式命令
        RedisPubSubReactiveCommands<String, String> reactive = connection.reactive();
        reactive.subscribe("channel").subscribe();

        reactive.observeChannels().doOnNext(new Consumer<ChannelMessage<String, String>>() {
            @Override
            public void accept(ChannelMessage<String, String> stringStringChannelMessage) {

            }
        }).subscribe();

    }

    /**
     * API
     * Lettuce主要提供三种API：
     * <p>
     * 同步（sync）：RedisCommands。
     * 异步（async）：RedisAsyncCommands。
     * 反应式（reactive）：RedisReactiveCommands
     */


    //拓扑发现机制
    @Test
    public void testDynamicReplica() throws Exception {
        // 这里只需要配置一个节点的连接信息，不一定需要是主节点的信息，从节点也可以
        RedisURI uri = RedisURI.builder().withHost("localhost").withPort(6379).build();
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, new Utf8StringCodec(), uri);
        // 只从从节点读取数据
        connection.setReadFrom(ReadFrom.SLAVE);
        // 执行其他Redis命令
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void testStaticReplica() throws Exception {
        List<RedisURI> uris = new ArrayList<>();
        RedisURI uri1 = RedisURI.builder().withHost("localhost").withPort(6379).build();
        RedisURI uri2 = RedisURI.builder().withHost("localhost").withPort(6380).build();
        RedisURI uri3 = RedisURI.builder().withHost("localhost").withPort(6381).build();
        uris.add(uri1);
        uris.add(uri2);
        uris.add(uri3);
        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient,
                new Utf8StringCodec(), uris);
        // 只从主节点读取数据
        connection.setReadFrom(ReadFrom.MASTER);
        // 执行其他Redis命令
        connection.close();
        redisClient.shutdown();
    }

    @Test
    public void testDynamicSentinel() throws Exception {
        RedisURI redisUri = RedisURI.builder()
                .withPassword("你的密码")
                .withSentinel("localhost", 26379)
                .withSentinelMasterId("哨兵Master的ID")
                .build();
        RedisClient redisClient = RedisClient.create();
        StatefulRedisMasterSlaveConnection<String, String> connection = MasterSlave.connect(redisClient, new Utf8StringCodec(), redisUri);
        // 只允许从从节点读取数据
        connection.setReadFrom(ReadFrom.SLAVE);
        RedisCommands<String, String> command = connection.sync();
        SetArgs setArgs = SetArgs.Builder.nx().ex(5);
        command.set("name", "throwable", setArgs);
        String value = command.get("name");
        System.out.println(value);
    }

    @Test
    public void testSyncCluster() {
        RedisURI uri = RedisURI.builder().withHost("192.168.56.200").build();
        RedisClusterClient redisClusterClient = RedisClusterClient.create(uri);
        StatefulRedisClusterConnection<String, String> connection = redisClusterClient.connect();
        RedisAdvancedClusterCommands<String, String> commands = connection.sync();
        commands.setex("name", 10, "throwable");
        String value = commands.get("name");
        System.out.println(value);
    }


    @Test
    public void testUseConnectionPool() throws Exception {
        RedisURI redisUri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(10, ChronoUnit.SECONDS))
                .build();
        RedisClient redisClient = RedisClient.create(redisUri);
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        GenericObjectPool<StatefulRedisConnection<String, String>> pool
                = ConnectionPoolSupport.createGenericObjectPool(redisClient::connect, poolConfig);
        try (StatefulRedisConnection<String, String> connection = pool.borrowObject()) {
            RedisCommands<String, String> command = connection.sync();
            SetArgs setArgs = SetArgs.Builder.nx().ex(5);
            command.set("name", "throwable", setArgs);
            String n = command.get("name");
            //log.info("Get value:{}", n);
        }
        pool.close();
        redisClient.shutdown();
    }
    //基于约定大于配置的原则


}
