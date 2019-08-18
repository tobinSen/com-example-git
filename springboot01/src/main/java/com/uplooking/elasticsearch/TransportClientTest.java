package com.uplooking.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.Map;

public class TransportClientTest {

    private static String index = "elasticsearchIndex";//索引
    private static String type = "elasticsearchType";//类型

    public static void main(String[] args) throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名
        //创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        //1.json方式添加索引
        String jsonSource = "{\"name\":\"张三\"}";
        IndexResponse indexResponse = client.prepareIndex(index, type, "1").setSource(jsonSource).get();
        System.out.println(indexResponse.getVersion());//悲观锁，版本号进行控制

        //2.map方式添加索引
        Map<String, Object> map = Maps.newHashMap();
        map.put("name", "张三");
        map.put("age", 12);
        IndexResponse indexResponse1 = client.prepareIndex(index, type, "2").setSource(map).get();

        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(index, type, "2");
        indexRequestBuilder.

        System.out.println(indexResponse1.getVersion());

        //3.通过bean来添加
        BigDataProduct bigDataProduct = new BigDataProduct("hadoop", "spark", "1.0.0");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = objectMapper.writeValueAsBytes(bigDataProduct);
        IndexResponse indexResponse2 = client.prepareIndex(index, type, "3").setSource(bytes).get();
        System.out.println(indexResponse2.getVersion());


        //4.ES helper来添加
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .field("name", "张三")
                .field("spark", "1.2")
                .field("version", "1.2")
                .endObject();
        IndexResponse indexResponse3 = client.prepareIndex(index, type, "4").setSource(builder).get();
        System.out.println(indexResponse3.getVersion());

        //ES查询
        GetResponse response = client.prepareGet(index, type, "1").get();
        System.out.println(response.getVersion());
        Map<String, Object> source = response.getSource();
        source.entrySet().forEach(System.out::println);

        //ES更新
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .field("name", "张三")
                .field("title", "上一周")
                .endObject();
        UpdateResponse updateResponse = client.prepareUpdate(index, type, "1").setDoc(xContentBuilder).get();
        System.out.println(updateResponse.getResult());

        //ES删除
        DeleteResponse deleteResponse = client.prepareDelete(index, type, "1").get();
        System.out.println(deleteResponse.getVersion());

        //ES统计
        long totalHits = client.prepareSearch(index)
                .setTypes(type)
                .execute()
                .actionGet()
                .getHits()
                .getTotalHits();
        System.out.println("查询到的总记录数：" + totalHits);

        //ES批量操作
        String deptDev = "{\"name\":\"研发部\", \"deptNo\" : 20}";
        String deptMarket = "{\"name\":\"市场部\", \"deptNo\" : 30}";
        String deptOffice = "{\" name \":\" 行政部\", \" deptNo\" : 40}";
        client.prepareBulk()
                .add(new IndexRequest(index, "dep", "1").source(deptDev))
                .add(new IndexRequest(index, "dep", "2").source(deptMarket))
                .add(new IndexRequest(index, "dep", "3").source(deptOffice))
                .add(new DeleteRequest(index, type, "3")).get();
    }
}
