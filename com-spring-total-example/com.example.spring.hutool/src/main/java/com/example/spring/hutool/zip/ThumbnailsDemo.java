package com.example.spring.hutool.zip;

import net.coobird.thumbnailator.Thumbnails;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class ThumbnailsDemo {
    public static void main(String[] args) throws IOException {
        Thumbnails.of(new URL("12"))
                .size(200, 200)
                .keepAspectRatio(false)
                .allowOverwrite(true)
                .toOutputStream(new FileOutputStream("1")); //压缩
    }
}
