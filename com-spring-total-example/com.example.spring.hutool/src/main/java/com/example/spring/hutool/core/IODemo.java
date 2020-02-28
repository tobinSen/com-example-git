package com.example.spring.hutool.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.watch.SimpleWatcher;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ObjectUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Properties;

public class IODemo {

    public static void main(String[] args) throws Exception {
//        BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
//        BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
//        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);

        FileReader fileReader = new FileReader("/Users/tongjian/Desktop/project.properties");
        String result = fileReader.readString();
        System.out.println(result);


        File file = FileUtil.file("project.properties");
        //这里只监听文件或目录的修改事件
        WatchMonitor.createAll(file, new SimpleWatcher() {
            @Override
            public void onModify(WatchEvent<?> event, Path currentPath) {
                Console.log("EVENT modify");
            }
        }).start();


        ClassPathResource resource = new ClassPathResource("test.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());

        Console.log("Properties: {}", properties);

        ObjectUtil.isBasicType(1);

    }
}
