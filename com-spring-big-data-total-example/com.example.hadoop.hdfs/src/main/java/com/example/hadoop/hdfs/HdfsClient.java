package com.example.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsClient {

    //上传
    @Test
    public void put() throws Exception {
        Configuration configuration = new Configuration();
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), configuration, "tongjian");
        fileSystem.copyFromLocalFile(new Path("/Users/tongjian/Downloads/test.rtf"), new Path("/"));
        fileSystem.close();
    }

    //下载
    @Test
    public void get() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        fileSystem.copyToLocalFile(new Path("/user/tongjian/hadoop/hdfs/input"), new Path("/Users/tongjian/Downloads/"));
        fileSystem.close();
    }

    //重命名
    @Test
    public void rename() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        fileSystem.rename(new Path("/test.rtf"), new Path("/test_1.rtf"));
        fileSystem.close();
    }

    //删除
    @Test
    public void delete() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        fileSystem.delete(new Path("/user/tongjian/hadoop/hdfs/input"), true);
        fileSystem.close();
    }

    //流复制
    @Test
    public void du() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        FSDataOutputStream append = fileSystem.append(new Path("/user/tongjian/hadoop/hdfs/input"), 1024);
        FileInputStream open = new FileInputStream("/Users/tongjian/Downloads/百安居WMS.txt");
        IOUtils.copyBytes(open, append, 1024, true);

    }

    //文件详情查看
    @Test
    public void list() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        RemoteIterator<LocatedFileStatus> iterator = fileSystem.listFiles(new Path("/"), true);
        while (iterator.hasNext()) {
            LocatedFileStatus next = iterator.next();
            System.out.println(next.getPath().getName());
            System.out.println(next.getLen());
            System.out.println(next.getPermission());
            System.out.println(next.getGroup());

            BlockLocation[] blockLocations = next.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
        }

        fileSystem.close();
    }

    @Test
    public void listFile() throws Exception {
        FileSystem fileSystem = FileSystem.get(URI.create("hdfs://localhost:9000"), new Configuration(), "tongjian");
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println(fileStatus.getPath().getName());
            } else {
                System.out.println(fileStatus.getPath().getName());
            }
        }
        fileSystem.close();
    }

    @Test
    public void putFileToHDFS() throws IOException, InterruptedException, URISyntaxException {

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "tongjian");

        // 2 创建输入流
        FileInputStream fis = new FileInputStream(new File("/Users/tongjian/Downloads/百安居WMS.txt"));

        // 3 获取输出流
        FSDataOutputStream fos = fs.create(new Path("/banhua.txt"));

        // 4 流对拷
        IOUtils.copyBytes(fis, fos, configuration);

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }

    @Test
    public void getFileFromHDFS() throws IOException, InterruptedException, URISyntaxException{

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "tongjian");

        // 2 获取输入流
        FSDataInputStream fis = fs.open(new Path("/banhua.txt"));

        // 3 获取输出流
        FileOutputStream fos = new FileOutputStream(new File("/Users/tongjian/Downloads/banhua.txt"));

        // 4 流的对拷
        IOUtils.copyBytes(fis, fos, configuration);

        // 5 关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
        fs.close();
    }

    @Test
    public void readFileSeek1() throws IOException, InterruptedException, URISyntaxException{

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:9000"), configuration, "tongjian");

        // 2 获取输入流
        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));

        // 3 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("e:/hadoop-2.7.2.tar.gz.part1"));

        // 4 流的拷贝
        byte[] buf = new byte[1024];

        for(int i =0 ; i < 1024 * 128; i++){
            fis.read(buf);
            fos.write(buf);
        }

        // 5关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
        fs.close();
    }

    @Test
    public void readFileSeek2() throws IOException, InterruptedException, URISyntaxException{

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://hadoop102:9000"), configuration, "atguigu");

        // 2 打开输入流
        FSDataInputStream fis = fs.open(new Path("/hadoop-2.7.2.tar.gz"));

        // 3 定位输入数据位置[重点]
        fis.seek(1024*1024*128);

        // 4 创建输出流
        FileOutputStream fos = new FileOutputStream(new File("e:/hadoop-2.7.2.tar.gz.part2"));

        // 5 流的对拷
        IOUtils.copyBytes(fis, fos, configuration);

        // 6 关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);
    }


}



