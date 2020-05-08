package algoiritm;

import java.util.*;

public class GreedyAlgorithm {

    public static void main(String[] args) {
        //创建广播电台
        Map<String, HashSet<String>> broadcasts = new HashMap<>();
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
//        hashSet3.add("上海");
        hashSet3.add("大连");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);

        //allArea存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");

        //存放已经选择的
        List<String> selects = new ArrayList<>();

        //临时集合，遍历过程中，
        Set<String> tempSet = new HashSet<>();

        //没有覆盖到所有的地区
        while (!allAreas.isEmpty()) {
            String maxKey = null;
            int maxValue = 0; //记录每次最大的匹配度
            for (String key : broadcasts.keySet()) {
                tempSet.clear();
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //取交集
                tempSet.retainAll(allAreas);
                //核心：获取匹配度最高的
                if (!tempSet.isEmpty() &&
                        //请求匹配数量>当一个的数量（隐藏信息：）
                        (maxKey == null || tempSet.size() > maxValue)) {
                    maxKey = key;
                    maxValue = tempSet.size();
                }
            }

            if (maxKey != null) {
                selects.add(maxKey);
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }


        System.out.println(selects);
    }

}
