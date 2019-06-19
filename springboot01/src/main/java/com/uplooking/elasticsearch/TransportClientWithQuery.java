package com.uplooking.elasticsearch;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.metrics.avg.AvgAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class TransportClientWithQuery {

    private static String index = "elasticsearchIndex";//索引
    private static String type = "elasticsearchType";//类型

    public static void main(String[] args) throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名
        //创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        SearchResponse response = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setFrom(1) //设置分页（查询第一页，每页显示2条记录）,公式：开始索引 = （页码-1）*pageSize
                .setSize(10)
                .setQuery(QueryBuilders.termQuery("name", "张三"))
                .get();
        //从结果中显示所有满足条件的记录
        SearchHits hits = response.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }

        //1，查询所有
        //matchAllQuery()匹配所有文件
        //match_all查询是Elasticsearch中最简单的查询之一。它使我们能够匹配索引中的所有文件
        SearchResponse searchResponse1 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .get();
        SearchHits hits1 = searchResponse1.getHits();// 获取命中次数，查询结果有多少对象
        //2，解析查询字符串
        //相比其他可用的查询，query_string查询支持全部的Apache Lucene查询语法
        //针对多字段的query_string查询
        SearchResponse searchResponse2 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.queryStringQuery("全面"))
                .get();
        SearchHits hits2 = searchResponse2.getHits();
        //3， 通配符查询（wildcardQuery）
        //*匹配多个字符，?匹配1个字符
        //注意：避免* 开始, 会检索大量内容造成效率缓慢
        SearchResponse searchResponse3 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.wildcardQuery("content", "else*c?"))
                .get();
        SearchHits hits3 = searchResponse3.getHits();
        //4，词条查询（termQuery）
        //词条查询是Elasticsearch中的一个简单查询。它仅匹配在给定字段中含有该词条的文档，而
        //且是确切的、未经分析的词条
        //termQuery("key", obj) 完全匹配
        //termsQuery("key", obj1, obj2..) 一次匹配多个值，只要有一个值是正确的，就可以查询出数据
        SearchResponse searchResponse4 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.termsQuery("content", "hadoop", "spark"))
                .get();
        SearchHits hits4 = searchResponse4.getHits();
        //5，字段匹配查询
        //matchQuery("key", Obj) 单个匹配, field不支持通配符, 前缀具高级特性
        //match查询把query参数中的值拿出来，加以分析，然后构建相应的查询。使用match查询时，Elasticsearch将对一
        //个字段选择合适的分析器，所以可以确定，传给match查询的词条将被建立索引时相同的分析器处理
        SearchResponse searchResponse5 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchQuery("content", "hadoop"))
                .setQuery(QueryBuilders.multiMatchQuery("zhSan", "title", "name"))
                .get();
        SearchHits hits5 = searchResponse5.getHits();
        //6，只查询ID（标识符查询）
        //标识符查询是一个简单的查询，仅用提供的标识符来过滤返回的文档。此查询针对内部的
        //_uid字段运行，所以它不需要启用_id字段
        SearchResponse searchResponse6 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.idsQuery().addIds("1"))
                .get();
        SearchHits hits6 = searchResponse6.getHits();
        //7，相似度查询
        //fuzzy查询是模糊查询中的第三种类型，它基于编辑距离算法来匹配文档
        SearchResponse searchResponse7 = client.prepareSearch("blog2")
                .setTypes("article")
                .setQuery(QueryBuilders.fuzzyQuery("content", "elasticsearxx"))
                .get();
        SearchHits hits7 = searchResponse7.getHits();
        //8，范围查询
        //范围查询使我们能够找到在某一字段值在某个范围里的文档，字段可以是数值型，也可以是
        //基于字符串的
        SearchResponse searchResponse8 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.rangeQuery("content").from("中台").to("wms")
                        .includeLower(true) //包含下线
                        .includeUpper(true)) //包含下线
                .get();
        SearchHits hits8 = searchResponse8.getHits();
        //9，跨度查询
        //下面代码表示，从首字母开始，查询content字段=问题的数据，问题前面的词为300个，可以测试30看是否能查询出数据
        SearchResponse searchResponse9 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.spanFirstQuery(QueryBuilders.spanTermQuery("content", "问题"),
                        300))
                .get();
        SearchHits hits9 = searchResponse9.getHits();
        //10，组合查询（复杂查询）
        //must(QueryBuilders) : AND
        //mustNot(QueryBuilders): NOT
        //should(QueryBuilders):OR
        SearchResponse searchResponse10 = client.prepareSearch("blog2")
                .setTypes("article")
                .setQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termQuery("title", "搜索"))
                        .must(QueryBuilders.wildcardQuery("content", "elastic*ch")))
                .get();
        SearchHits hits10 = searchResponse10.getHits();
        //11排序查询
        SearchResponse searchResponse11 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.matchAllQuery())
                .addSort("id", SortOrder.DESC)//查询后，在进行排序
                .get();
        SearchHits hits11 = searchResponse11.getHits();
        //12 ES查询之regexpQuery() 正则表达式查询
        SearchResponse searchResponse12 = client.prepareSearch(index)
                .setTypes(type)
                .setQuery(QueryBuilders.regexpQuery("num", "W[0-9]"))
                .addSort("id", SortOrder.DESC)//查询后，在进行排序
                .get();
        SearchHits hits12 = searchResponse12.getHits();
        //13.分页ES查询详解之分页from/size
        SearchResponse searchResponse13 = client.prepareSearch(index)
                .setTypes("account")
                //设置检索的条件
                .setQuery(QueryBuilders.termQuery("gender.keyword", "M"))
                //定制排序规则
                //①根据得分降序排列
                .addSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                //②若得分相同，根据银行存款余额的升序排列
                .addSort(SortBuilders.fieldSort("balance").order(SortOrder.ASC))
                //设置分页
                .setFrom(0)
                .setSize(1000)
                //触发检索
                .get();
        //14ES查询详解之过滤filter
        //.setPostFilter(FilterBuilders.rangeFilter("age").from(1).to(19))
        //15.ES查询详解之高亮highlight
        String indices = "bigdata";//指的是要搜索的哪一个索引库
        SearchRequestBuilder builder = client.prepareSearch(indices)
                .setSearchType(SearchType.DEFAULT)
                .setFrom(0)
                .setSize(5);//设置分页
                /*.highlighter(HighlightBuilder.fromXContent(new QueryParseContext("")))
                .setHighlighterPreTags("<font style='color:red;size=35'>")
                .setHighlighterPostTags("</font>");//高亮风格*/
        //16.ES查询详解之aggregations
        //根据字段进行分组统计 根据字段分组，统计其他字段的值 size设置为0，会获取所有数据，否则，只会返回10条
        SearchResponse searchResponse14 = client.prepareSearch(indices)
                .setTypes("account")
                //设置检索的条件
                .setQuery(QueryBuilders.termQuery("gender.keyword", "F"))
                //设置聚合操作
                //①员工数
                .addAggregation(new ValueCountAggregationBuilder("cntEmp",
                        ValueType.LONG).field("account_number"))
                //②最年轻的（女性）员工的年龄
                .addAggregation(new MinAggregationBuilder("minAge").field("age"))
                //③（女性员工）最少银行存款余额
                .addAggregation(new MinAggregationBuilder("minBalance").field("balance"))
                //④（女性员工）平均银行存款余额
                .addAggregation(new AvgAggregationBuilder("avgBalance").field("balance"))
                //触发检索
                .get();
    }

}
