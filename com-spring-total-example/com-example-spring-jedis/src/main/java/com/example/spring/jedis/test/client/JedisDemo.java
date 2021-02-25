package com.example.spring.jedis.test.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CountDownLatch;

public class JedisDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Jedis jedis = new Jedis("127.0.0.1", 6379);

        jedis.configSet("notify-keyspace-events", "Kx");

        jedis.setex("key", 5, "val");
        jedis.setex("key:tongjian", 5, "val");

//        jedis.subscribe(new JedisPubSub() {
//            @Override
//            public void onMessage(String channel, String message) {
//                System.out.println("channel:" + channel + "message:" + message);
//            }
//
//            @Override
//            public void onPMessage(String pattern, String channel, String message) {
//                System.out.println("pattern" + pattern + "channel:" + channel + "message:" + message);
//            }
//
//            @Override
//            public void onSubscribe(String channel, int subscribedChannels) {
//                System.out.println("channel:" + channel + "s" + subscribedChannels);
//            }
//
//            @Override
//            public void onUnsubscribe(String channel, int subscribedChannels) {
//                System.out.println("channel:" + channel + "s" + subscribedChannels);
//            }
//
//            @Override
//            public void onPUnsubscribe(String pattern, int subscribedChannels) {
//                System.out.println("pattern:" + pattern + "s" + subscribedChannels);
//            }
//
//            @Override
//            public void onPSubscribe(String pattern, int subscribedChannels) {
//                System.out.println("pattern:" + pattern + "s" + subscribedChannels);
//            }
//        }, "__keyspace@0__:key"); // subscribe 不支持通配

        jedis.psubscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println("channel:" + channel + "message:" + message);
            }

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println("pattern" + pattern + "channel:" + channel + "message:" + message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                System.out.println("channel:" + channel + "s" + subscribedChannels);
            }

            @Override
            public void onUnsubscribe(String channel, int subscribedChannels) {
                System.out.println("channel:" + channel + "s" + subscribedChannels);
            }

            @Override
            public void onPUnsubscribe(String pattern, int subscribedChannels) {
                System.out.println("pattern:" + pattern + "s" + subscribedChannels);
            }

            @Override
            public void onPSubscribe(String pattern, int subscribedChannels) {
                System.out.println("pattern:" + pattern + "s" + subscribedChannels);
            }
        },"__keyspace*__:key:*"); // psubscribe 支持通配 "__key*__:*" 或者 __keyspace*__:key:* 或者 __keyspace@?__:key:*匹配




        latch.await();

    }
}
