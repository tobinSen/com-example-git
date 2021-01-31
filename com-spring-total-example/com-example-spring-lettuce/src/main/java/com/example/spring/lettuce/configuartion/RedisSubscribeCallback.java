package com.example.spring.lettuce.configuartion;

public interface RedisSubscribeCallback {
    void callback(String msg);
}
