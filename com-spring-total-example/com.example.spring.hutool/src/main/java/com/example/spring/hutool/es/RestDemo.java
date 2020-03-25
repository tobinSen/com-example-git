package com.example.spring.hutool.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RestDemo {

    public static void main(String[] args) throws Exception{
        //lowLevel
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")).build();

//        RestClientBuilder builder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
//        Header[] defaultHeaders = new Header[]{new BasicHeader("header", "value")};
//        builder.setDefaultHeaders(defaultHeaders);
//        builder.setMaxRetryTimeoutMillis(10000);
//        builder.setFailureListener(new RestClient.FailureListener() {
//            @Override
//            public void onFailure(HttpHost host) {
//                // TODO
//            }
//        });
        String method = "PUT";
        String endpoint = "/test-index";
        Response response = restClient.performRequest(method,endpoint);

        //highLevel
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        IndexRequest request = new IndexRequest(
                "posts",  // 索引 Index
                "doc",  // Type
                "1");  // 文档 Document Id
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON); // 文档源格式为 json string

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "kimchy");
        jsonMap.put("postDate", new Date());
        jsonMap.put("message", "trying out Elasticsearch");
        IndexRequest indexRequest = new IndexRequest("posts", "doc", "1")
                .source(jsonMap);  // 会自动将 Map 转换为 JSON 格式

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "kimchy");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch");
        }
        builder.endObject();
        IndexRequest indexRequest1 = new IndexRequest("posts", "doc", "1")
                .source(builder);

        IndexRequest indexRequest2 = new IndexRequest("posts", "doc", "1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        IndexResponse indexResponse = restHighLevelClient.index(request);


        GetRequest getRequest = new GetRequest(
                "posts",
                "doc",
                "1");
        GetResponse getResponse = restHighLevelClient.get(getRequest);

        GetRequest getRequest1 = new GetRequest(
                "posts",  // Index
                "doc",    // Type
                "1");     // Document id
        getRequest.fetchSourceContext(new FetchSourceContext(false));  // 禁用 _source 字段
        getRequest.storedFields("_none_"); // 禁止存储任何字段


        DeleteRequest request3 = new DeleteRequest(
                "posts",   // index
                "doc",     // doc
                "1");      // document id

        UpdateRequest request4 = new UpdateRequest(
                "posts",  // Index
                "doc",  // 类型
                "1");   // 文档 Id


        BulkRequest request5 = new BulkRequest();
        request5.add(new IndexRequest("posts", "doc", "1")
                .source(XContentType.JSON,"field", "foo")); // 将第一个 IndexRequest 添加到批量请求中
        request5.add(new IndexRequest("posts", "doc", "2")
                .source(XContentType.JSON,"field", "bar")); // 第二个
        request5.add(new IndexRequest("posts", "doc", "3")
                .source(XContentType.JSON,"field", "baz")); // 第三个

        MultiGetRequest request6 = new MultiGetRequest();
        request6.add(new MultiGetRequest.Item(
                "index",     // 索引
                "type",      // 类型
                "example_id"));  // 文档 id
        request6.add(new MultiGetRequest.Item("index", "type", "another_id"));  // 添加另外一个条目
//        BulkProcessor bulkProcessor =
//                BulkProcessor.builder(restHighLevelClient::bulkAsync, listener).build();
    }

}
