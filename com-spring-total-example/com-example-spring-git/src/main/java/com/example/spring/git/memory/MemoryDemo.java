package com.example.spring.git.memory;

import org.openjdk.jol.info.ClassLayout;

public class MemoryDemo {

    public static void main(String[] args) {
        int [] arr = new int[100];
        byte[] bytes = new byte[1101111111];
        int[][] ints = new int[2][100];
        int[][] ints1 = new int[100][2];
//        System.out.println(ClassLayout.parseInstance(ints).toPrintable()); // 16 bytes 对象头 + 实例数据 + 对齐填充
        //System.out.println(ClassLayout.parseInstance(new Integer(1)).toPrintable());
//        System.out.println(VM.current().details());
        // 8 + 4 + 4  char[] 16 + 2 = 24
        System.out.println(ClassLayout.parseInstance(new String()).toPrintable());
    }
}
