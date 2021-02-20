package com.example.spring.git.fastutil;

import it.unimi.dsi.fastutil.ints.*;
import it.unimi.dsi.fastutil.longs.Long2IntAVLTreeMap;
import it.unimi.dsi.fastutil.longs.Long2IntSortedMap;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;

public class FastutilDemo {

    public static void main(String[] args) {
        intList();
        LongList();
    }

    private static void intList() {
        //===========IntList
        IntList list = new IntArrayList();

        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        //取值
        int value = list.getInt(0);
        System.out.println(value);// 0

        //转成数组
        int[] values = list.toIntArray();
        System.out.println(values.length);// 1000

        //遍历
        IntListIterator i = list.iterator();
        while (i.hasNext()) {
            System.out.println(i.nextInt());
        }

        //===========Int2BooleanMap
        Int2BooleanMap map = new Int2BooleanArrayMap();

        map.put(1, true);
        map.put(2, false);

        //取值
        boolean value1 = map.get(1);
        boolean value2 = map.get(2);

        System.out.println(value1);// true
        System.out.println(value2);// false

        //===========IntBigList
        IntBigList biglist = new IntBigArrayBigList();

        biglist.add(0);
        biglist.add(1);
        biglist.add(2);

        long size = biglist.size64();

        //取值
        for (long index = 0; index < size; index++) {
            System.out.println(biglist.getInt(index));
        }

        //===========IntSortedSet
        IntSortedSet s = new IntLinkedOpenHashSet(new int[]{4, 3, 2, 1});
        //获取第一个元素
        System.out.println(s.firstInt()); // 4
        //获取最后一个元素
        System.out.println(s.lastInt()); // 1
        //判断是否包含一个元素
        System.out.println(s.contains(5)); // false
    }

    private static void LongList() {
        Long2IntSortedMap m = new Long2IntAVLTreeMap();

        m.put(1, 5);
        m.put(2, 6);
        m.put(3, 7);
        m.put(1000000000L, 10);

        System.out.println(m.get(1)); // 5

        //当查找不到的时候，默认返回0
        System.out.println(m.get(4)); // 0

        //设置默认返回值
        m.defaultReturnValue(-1);
        System.out.println(m.get(4)); // -1

        //遍历Map
        LongBidirectionalIterator key1 = m.keySet().iterator();
        long s = 0;
        while (key1.hasNext()) {
            s += key1.nextLong();
        }
        System.out.println(s); // 1000000006

        //获取Key值小于4的子Map
        Long2IntSortedMap m1 = m.headMap(4);
        LongBidirectionalIterator key2 = m1.keySet().iterator();
        while (key2.hasNext()) {
            System.out.println(key2.nextLong());
        }
    }
}
