package com.example.spring.hutool.zip;

import cn.hutool.core.util.ZipUtil;

import java.io.*;

public class ThumbnailsDemo {
    public static void main(String[] args) throws IOException {
//        Thumbnails.of(new URL("12"))
//                .size(200, 200)
//                .keepAspectRatio(false)
//                .allowOverwrite(true)
//                .toOutputStream(new FileOutputStream("1")); //压缩

        File zipFile = ZipUtil.zip("/Users/tongjian/Downloads/sijibao-api-common-0.0.1","/Users/tongjian/Downloads/sijibao-api-common-0.0.1.zip");

    }
}
