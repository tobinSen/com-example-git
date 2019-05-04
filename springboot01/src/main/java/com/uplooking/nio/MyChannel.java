package com.uplooking.nio;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

/**
 * 通道（channel）用于源节点与目标节点的连接，在java NIO中负责缓冲区中数据传输
 * Channel本身不存储数据
 * <p>
 * FileChannel:本地
 * SocketChannel ：网络 TCP
 * ServerChannel ：网络 TCP
 * DatagramChannel : UDP
 * <p>
 * 获取通道：
 * 1、Java针对支持通道的类提供了getChannel()方法
 * 本地IO
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 网络IO
 * Socket
 * ServerSocket
 * DatagramSocket
 *  Pipe.SinkChannel
 *  Pipe.SourceChannel
 * 2、在JDK1.7中NIO针对各个通道提供了静态方法open()
 * 3、在JDK1.7中NIO中Files工具类newByteChannel()
 * <p>
 * 通道之间的数据传输
 * transferFrom()
 * transferTo()
 * <p>
 * 分撒(Scatter)与聚集（Gather）
 * 分撒读取(Scattering Reads)：将通道中的数据分散列多个缓冲区中
 * 按照顺序将通道数据写入到缓冲区中
 * <p>
 * 聚集写入(Gather Writers) : 将多个缓冲区中的数据聚集到通道中
 * 按照顺序将缓冲区中的数据写入到通道中
 *
 * 字符集 CharSet
 * 编码：字符串->字节数组
 * 解码：字节数组->字符串
 */
public class MyChannel {

    @SneakyThrows
    public static void main(String[] args) {
        //通道完成文件的复制
        FileInputStream fis = new FileInputStream("1.jpg");
        FileOutputStream fos = new FileOutputStream("2.jpg");

        //1、获取通道（fileChannel本质还是IO,读写数据）
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        //2、分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        //3、将通道中的数据存入缓冲区
        while (inChannel.read(buf) != -1) {
            buf.flip();//切换成读数据模式
            outChannel.write(buf);
            buf.clear();//清空缓冲区
        }
        outChannel.close();
        inChannel.close();
        fos.close();
        fis.close();

        //使用直接缓冲区完成文件的复制(内存映射文件)
        FileChannel inFileChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outFileChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
        //内存映射文件
        MappedByteBuffer inBuffer = inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
        MappedByteBuffer outBuffer = inFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, inFileChannel.size());

        //直接对缓冲区进行数据你读写操作
        byte[] dst = new byte[inBuffer.limit()];
        inBuffer.get(dst);
        outBuffer.put(dst);

        inFileChannel.close();
        outFileChannel.close();

        //通道之间的数据传输
        inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        //1、获取通道
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        //2、分配指定大小的缓存区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3、分撒读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        //4、聚集写入
        RandomAccessFile accessFile = new RandomAccessFile("2.txt","rw" );


        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : charsets.entrySet()) {

        }

        Charset cs1 = Charset.forName("GBK");
        CharsetEncoder encoder = cs1.newEncoder();
        CharsetDecoder decoder = cs1.newDecoder();
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put("尚硅谷");
        buffer.flip();

    }


}
