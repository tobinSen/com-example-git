package com.uplooking.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.sql.Timestamp;
import java.util.List;

public class MyMongoTemplate {


    @Autowired
    private MongoTemplate mongoTemplate;

    public void test() {
        Book book = new Book();
        book.setId(1L);
        book.setContext("mongo");
        book.setTime(new Timestamp(System.currentTimeMillis()));
        mongoTemplate.save(book, "book");

        Query query = Query.query(Criteria.where("id").is(1L));
        mongoTemplate.remove(query, Book.class);

        Query query1 = Query.query(Criteria.where("id").is(2L));
        Update update = Update.update("context", "mongoTemplate").set("title", "mongoForUpdate");
        mongoTemplate.updateMulti(query1, update, Book.class);

        Query query2 = Query.query(Criteria.where("id").in(1L, 3L));
        List<Book> books = mongoTemplate.find(query2, Book.class);
    }
}
