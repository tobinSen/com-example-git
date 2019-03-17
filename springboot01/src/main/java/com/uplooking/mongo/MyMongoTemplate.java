package com.uplooking.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class MyMongoTemplate {


    @Autowired
    private MongoTemplate mongoTemplate;

    public void test() {
        Book book = new Book();
        book.setId(1L);
        book.setContext("mongo");
        book.setTime(new Timestamp(System.currentTimeMillis()));
        mongoTemplate.save(book, "book");

        Query query = Query.query(Criteria.where("id").is(1L).and("name").is("三国演义").orOperator(Criteria.where("context").is("三国演义")));
        mongoTemplate.remove(query, Book.class);

        Query query1 = Query.query(Criteria.where("id").is(2L)).with(new Sort(Sort.Direction.DESC, "name")).skip(1).limit(2);
        /**
         * 两条数据匹配时，upsert 将只会更新一条数据
         */
        Update update = Update.update("context", "mongoTemplate")
                .set("title", "mongoForUpdate").addToSet("addToSet", "新增字段")
                .inc("age", 20L)
                .currentDate("date")
                .rename("oldTitle", "newTitle")
                .unset("title")
                .push("push", "newPush")
                .addToSet("add").each(1, 2, 3, 4, 5)// 批量插入数据到数组中, 注意不会将重复的数据丢入mongo数组中
                .pull("add", 1);
        // 内嵌doc新增field
        Update update1 = new Update().set("doc.title", "好好学习，天天向上!");
        // 删除内嵌doc中的field
        Update update2 = new Update().unset("doc.title");

        mongoTemplate.updateMulti(query1, update, Book.class);

        Query query2 = Query.query(Criteria.where("id").in(1L, 3L));
        List<Book> books = mongoTemplate.find(query2, Book.class);

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.group("id").count().as("userId"));
        AggregationResults<Map> collectionName = mongoTemplate.aggregate(aggregation, "collectionName", Map.class);
        List<Map> mappedResults = collectionName.getMappedResults();


    }
}
