package com.example.spring.elasticsearch.example;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class Demo {

    private static String index = "province";//索引
    private static String type = "city";//类型

    public static void main(String[] args) throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "docker-cluster").build();// 集群名
        //创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //1.json方式添加索引
//        String jsonSource = "{\"name\":\"李四\",\"age:\":12}";
//        IndexResponse indexResponse = client.prepareIndex(index, type, "1").setSource(jsonSource).get();
//        System.out.println(indexResponse.getVersion());//悲观锁，版本号进行控制

//        XContentBuilder builder = XContentFactory.jsonBuilder()
//                .startObject()
//                .field("name", "张三")
//                .field("spark", "1.2")
//                .field("version", "1.2")
//                .endObject();
//        IndexResponse indexResponse3 = client.prepareIndex(index, type, "4").setSource(builder).get();
//        System.out.println(indexResponse3.getVersion());

//        GetResponse response = client.prepareGet(index, type, "1").get();
//        System.out.println(response.getVersion());
//        Map<String, Object> source = response.getSource();
//        source.entrySet().forEach(System.out::println);

//        DeleteResponse deleteResponse = client.prepareDelete(index, type, "1").get();
////        System.out.println(deleteResponse.getVersion());

//        long totalHits = client.prepareSearch(index)
//                .setTypes(type)
//                .execute()
//                .actionGet()
//                .getHits()
//                .getTotalHits();
//        System.out.println("查询到的总记录数：" + totalHits);


        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(1) //设置分页（查询第一页，每页显示2条记录）,公式：开始索引 = （页码-1）*pageSize
                .setSize(10)
                .setQuery(QueryBuilders.termQuery("name", "张三"))
                .get();

    }

}
