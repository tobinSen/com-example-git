package com.uplooking.guava.utilites;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;

import java.util.Set;

public class MyUtilites {

    public static void main(String[] args) {
        /*
        List<Integer> list = Arrays.asList(1, 4, 2, 6, 3, 7);

        list.sort(Comparator.reverseOrder());
        System.out.println(list.toString());
        list.sort(Comparator.naturalOrder());
        System.out.println(list.toString());


        boolean flag = Objects.equals(1, "1");
        System.out.println(flag);
        Objects.compare(1, 2, (x, y) -> x > y ? 1 : 0);
        flag = Objects.deepEquals(1, "1");
        


        HashMultiset<Object> multiset = HashMultiset.create();
        multiset.add(1, 2);
        Set<Multiset.Entry<Object>> entries = multiset.entrySet();
        for (Multiset.Entry<Object> entry : entries) {
            System.out.println(entry.getElement() + "-->" + entry.getCount());
        }
        Set<Object> objects = multiset.elementSet();
        System.out.println(objects);
        multiset.forEach(System.out::println);

        HashMultimap<Object, Object> multimap = HashMultimap.create();
        multimap.put(1, 2);
        multimap.put(1, 2);
        multimap.put(1, 2);
        Set<Map.Entry<Object, Object>> entrySet = multimap.entries();
        for (Map.Entry<Object, Object> entry : entrySet) {
            System.out.println(entry.getKey() + "===>" + entry.getValue());
        }
        BiMap<String, Object> biMap = HashBiMap.create();
        BiMap<Object, String> inverse = biMap.inverse();

        CaseFormat[] values = CaseFormat.values();
        for (CaseFormat value : values) {
            System.out.println(value);

        }
        String aaaa = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL, "aaaa");
        System.out.println(aaaa);
        ArrayDeque<Object> objects1 = Queues.newArrayDeque();
        String join = Ints.join(",", 1, 2, 3, 4);
        int max = Ints.max(1, 2, 4, 11, 56);
        System.out.println(join);
        System.out.println(max);
        Integer[] array = ObjectArrays.newArray(Integer.class, 4);
        IntMath.checkedAdd(1, Integer.MAX_VALUE);
        MoreObjects.firstNonNull(1, 2);
*/
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
        Set<Integer> set2 = Sets.newHashSet(4, 5, 6, 7);
        //交集
        Sets.SetView<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
        //差集（set1 - set2）
        Sets.SetView<Integer> difference = Sets.difference(set1, set2);
        System.out.println(difference);
        //补集
        Sets.SetView<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
        Cache cache = CacheBuilder.newBuilder().build();

//        Table<R, C, V2> rcv2Table = Tables.transformValues(HashBasedTable < String, String, String >.create(), Functions.
//        compose(input -> input, input -> input));
    }
}
