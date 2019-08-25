package com.example.spring.mybatis.guava;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Guava {

    private static class User {
        private Long id;
        private String name;
        private Integer age;

        public User(Long id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    public static void main(String[] args) {
        Guava.User user1 = new User(1L, "tom", 11);
        Guava.User user2 = new User(1L, "joy", 12);
        Guava.User user3 = new User(3L, "peter", 13);

        ArrayList<User> users = Lists.newArrayList(user1, user2, user3);
        //Map<Long, User> index = Maps.uniqueIndex(users, User::getId);//只能唯一
        Map<Long, User> map = users.stream().collect(HashMap::new, (hashMap, user) -> hashMap.put(user.getId(), user), HashMap::putAll);

        System.out.println(map.toString());


    }

}
