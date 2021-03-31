package com.example.spring.jedis.test.client;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class JedisDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        JedisShardInfo jedisShardInfo = new JedisShardInfo("", 8080);
        jedisShardInfo.setPassword("");
        Jedis jedis = new Jedis(jedisShardInfo);


        jedis.del("key_scan");

        for (int i = 0; i < 513; i++) {
            jedis.sadd("key_scan", String.valueOf(i));
        }

        String cursor = ScanParams.SCAN_POINTER_START;
        ScanParams params = new ScanParams();
        params.count(5173);
        Set<String> data = new HashSet<>();
        for (; ; ) {
            ScanResult<String> sscan = jedis.sscan("key_scan", cursor, params);
            cursor = sscan.getStringCursor();
            List<String> result = sscan.getResult();
            data.addAll(result);
            if (cursor.equals("0")) {
                break;
            }
        }
        System.out.println(data);


//        jedis.configSet("notify-keyspace-events", "Kx");

//        jedis.setex("key", 5, "val");
//        jedis.setex("key:tongjian", 5, "val");

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

//        jedis.psubscribe(new JedisPubSub() {
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
//        },"__keyspace*__:key:*"); // psubscribe 支持通配 "__key*__:*" 或者 __keyspace*__:key:* 或者 __keyspace@?__:key:*匹配


        latch.await();

    }
}
