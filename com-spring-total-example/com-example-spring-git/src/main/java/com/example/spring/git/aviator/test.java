package com.example.spring.git.aviator;

import com.google.common.collect.Lists;

import java.util.List;

public class test {

    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 3) {
                list.remove(i);
            }
            System.out.println("i:" + list.get(i));
        }

        System.out.println(list);
    }
}
