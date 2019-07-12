package com.uplooking.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegex {
    /**
     * Pattern:
     * Matcher
     *   matches ->Group
     *   find    ->Group
     *   lookingAt ->Group
     */

    //find方法测试
    public static void find(String html) {
        String regex = "\\d\\d\\d";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        System.out.print("find():");
        if (matcher.find()) {
            System.out.println(matcher.group());
        }

    }
    //matches方法测试
    public static void matches(String html) {
        String regex = "^\\w\\d\\d\\d";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        System.out.print("matches():");
        if (matcher.matches()) {
            System.out.println(matcher.group());
        }

    }
    //lookingAt方法测试
    public static void lookingAt(String html) {
        String regex = "\\w\\d\\d";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);
        System.out.print("lookingAt():");
        if (matcher.lookingAt()) {
            System.out.println(matcher.group());
        }

    }

    /**
     * 输出结果：
     *     find():123
     *     matches():a123
     *     lookingAt():a12
     */
    public static void main(String[] args) {
        //find方法测试
        find("a123b");
        //matches方法测试
        matches("a123");
        //lookingAt方法测试
        lookingAt("a123b");
    }
}


