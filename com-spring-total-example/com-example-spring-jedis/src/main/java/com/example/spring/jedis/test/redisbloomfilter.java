package com.example.spring.jedis.test;

import io.rebloom.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class redisbloomfilter {

    @Bean
    public JedisPool jedisPool() {
        JedisPool jp = new JedisPool();
        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(redisProperties.getPoolMaxIdle());
//        poolConfig.setMaxTotal(redisProperties.getPoolMaxTotal());
//        poolConfig.setMaxWaitMillis(redisProperties.getPoolMaxWait() * 1000);
//        JedisPool jp = new JedisPool(poolConfig, redisProperties.getHost(), redisProperties.getPort(),
//                redisProperties.getTimeout()*1000, redisProperties.getPassword(), 0);
        return jp;
    }

    @Bean
    public Client client(JedisPool pool) {
        return new Client(pool);
    }

    @Autowired
    private Client client;

    public boolean bfCreate(String key, long initCapacity, double errorRate) {
        try {
            client.createFilter(key, initCapacity, errorRate);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bfAdd(String key, String value) {
        try {
            return client.add(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean bfExists(String key, String value) {
        try {
            return client.exists(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String key) {
        try {
            client.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
