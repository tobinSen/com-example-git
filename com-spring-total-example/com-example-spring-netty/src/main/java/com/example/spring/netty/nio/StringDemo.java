package com.example.spring.netty.nio;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * 编解码问题：
 *    ASCII：7 bit表示一个字符，共计可以表示128种字符
 *    ISO-8859-1: 8 bit表示一个字符，即用一个字节(byte)(8bit) 来表示一个字符，共计可以表示256个字符
 *    gb2312：两个字节表示一个汉字
 *    gbk：将生僻字加入其中
 *    gb18030：全的汉字
 *
 *    big5：台湾
 *
 *   unicode：表示全世界的编码，采用2个字节表示一个字符（存储量增加了）
 *
 *   UTF：是一种存储格式
 *
 *   utf-8 : Unicode Translate Format:变长字节表示形式 3字节表示一个中文，
 *   utf-16
 *   utf-32
 *
 *   unicode 是一种编码方式，而UTF这是一种存储方式：
 *
 *   BOM(Byte order Mark)：是一个window的遗留问题。
 *   磁盘存在文件方式：
 *   文件的编码方式：
 *
 *
 *
 *
 *
 *
 */
public class StringDemo {

    public static void main(String[] args) {
        Charset charset = Charset.forName("utf-8");
        //解码对象
        CharsetDecoder decoder = charset.newDecoder();
        //编码对象
        CharsetEncoder encoder = charset.newEncoder();


        Charset.availableCharsets().forEach((s, charset1) ->{
            System.out.println(s);
            System.out.println(charset1);
        });
    }
}
