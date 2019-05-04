package com.uplooking.nio;

import java.nio.ByteBuffer;

/**
 * put():存入数据到缓存区
 * get():获取缓存区的数据
 * <p>
 * 缓冲区中的四个核心属性
 * capacity:容量，表示缓冲区中最大存储数据的容量，一旦声明就不能改变
 * limit:界限，表示缓存区中可以操作数据的大小，(limit后数据不能进行读写)
 * position:位置，表示缓冲区中正在操作数据的位置
 *
 * 0<=mark<=position<=limit<=capacity
 *
 * mark:标记，表示记录当前position的位置，可以通过reset()恢复到mark的位置
 *
 * 五、直接缓存区与非直接缓冲区
 * 非直接缓冲区：通过allocate()方法分配缓冲区，将缓冲区建立在jvm的内存中
 * 直接缓冲区：通过allocateDirect()方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class MyNIO {

    public static void main(String[] args) {
        String str = "abcde";
        //1、分配一个指定大小的缓冲区（缓冲区=数组）
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("---------allocate--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.put(str.getBytes());
        System.out.println("---------put--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //转换为读取状态position=0 ，limit= str.length()
        buffer.flip();
        System.out.println("---------flip--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("---------get--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //可重复读
        buffer.rewind();
        System.out.println("---------rewind--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        //缓冲区被清空，但是数据还存在，只是数据处于被遗忘状态
        buffer.clear();
        System.out.println("---------clear--------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.mark();//标记
        buffer.reset();//恢复mark的位置
        buffer.hasRemaining();//缓冲区中是否妖后的数据
        buffer.isDirect();//判断是否是直接缓冲区
    }
}
