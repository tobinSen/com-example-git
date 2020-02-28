package com.example.spring.hutool.util;

import cn.hutool.extra.emoji.EmojiUtil;

public class EmojiDemo {

    public static void main(String[] args) {
        String emoji = EmojiUtil.toUnicode(":smile:");//😄
        System.out.println(emoji);

    }
}
