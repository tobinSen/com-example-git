package com.example.spring.mybatis.domain;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class User {

    private Integer userId;

    private String userName;

    private String password;

    private String phone;

    private Date date;

    private List<City> city;
    //private List<City> city;

    public class City {

        private String name;

        private String location;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<City> getCity() {
        return city;
    }

    public void setCity(List<City> city) {
        this.city = city;
    }

    static class DataObject {
        private final String data = "";

        private static int objectCounter = 0;
        // standard constructors/getters

        public static DataObject get(String data) {
            objectCounter++;
            return new DataObject();
        }
    }

    public static void main(String[] args) {
        IntStream.rangeClosed(1, 5).forEach(n ->
                Stream.generate(() -> "user").limit(1)
                        .map(x -> n + ":" + x)
                        .forEach(System.out::println));

        List<String> list = Arrays.asList("zhangsan", "lisi", "wangwu");
        AtomicInteger i = new AtomicInteger(1);
        list.forEach(x -> {
            System.out.println(i + ":" + x);
            i.getAndIncrement();
        });

        //1.手动填充
        Cache<String, DataObject> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
        //存在返回，不存在返回null
        DataObject key = cache.getIfPresent("key");
        cache.put("key", new DataObject());
        //如果缓存中不存在该 key，则该函数将用于提供默认值，该值在计算后插入缓存中
        DataObject dataObject = cache
                .get("key", k -> DataObject.get("Data for A "));

        //2.同步加载
        LoadingCache<String, DataObject> loadingCache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .build(k -> DataObject.get("Data for " + k));

        //3.异步加载
        AsyncLoadingCache<String, DataObject> asyncLoadingCache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync(k -> DataObject.get("Data for " + k));

        asyncLoadingCache.get("key").thenRun(() -> {});
        

    }
}
