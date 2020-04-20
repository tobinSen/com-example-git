package com.example.spring.hutool.threads;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 编程：假设有一个A站点，每次登陆的时候都会记录用户的登陆信息日志，
 * 对象类型是User，有3个字段： userId, userName, loginTime
 * 需求：统计最近10天（每天约10亿条日志）登陆最频繁的10个用户，
 * 排序并输出出这10个用户各登陆多少次
 * 要求：a. 不可直接使用SQL统计，请使用JAVA代码编写
 * <p>
 * <p>
 * 篮子里有100个苹果，将这些苹果进行编号，从0开始到99，
 * 现在将这些苹果分成4份，第一份21个，第二份30个，第三份38个，
 * 最后一份11个，要求是苹果的编号不能连续
 */


public class Demo {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }

        List<Integer> one = new ArrayList<>(21);
        List<Integer> two = new ArrayList<>(30);
        List<Integer> three = new ArrayList<>(38);
        List<Integer> four = new ArrayList<>(11);

        for (Integer appleNum : list) {
            if (one.size() != 21) {
                one.add(appleNum);
                continue;
            }
            if (two.size() != 30) {
                two.add(appleNum);
                continue;
            }
            if (three.size() != 38) {
                three.add(appleNum);
                continue;
            }
            if (four.size() != 11) {
                four.add(appleNum);
            }
        }

        Collections.shuffle(one);
        Collections.shuffle(two);
        Collections.shuffle(three);
        Collections.shuffle(four);

        System.out.println("one:" + one.size() + "===>" + one.toString());
        System.out.println("two:" + two.size() + "===>" + two.toString());
        System.out.println("three:" + three.size() + "===>" + three.toString());
        System.out.println("four:" + four.size() + "===>" + four.toString());
    }


}
