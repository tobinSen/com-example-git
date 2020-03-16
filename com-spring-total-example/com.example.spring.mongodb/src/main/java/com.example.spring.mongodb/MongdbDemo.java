package com.example.spring.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongdbDemo {

    public static void main(String[] args) {
        // 1.获取mongo客户端
        MongoClient client = new MongoClient("localhost", 27017);
        // 2.获取mongo指定数据库对象
        MongoDatabase database = client.getDatabase("mongodb");
        // 3.获取集合
        MongoCollection<Document> collection = database.getCollection("mongodb_collection");
        // 4.获取文档数据

        /**                 BSONObject  map
         *           List
         *                   DBObject   Bson
         *
         *          BasicBSONList   BasicBSONObject
         *
         *          BasicDBList   BasicBSONObject
         *
         */
        BasicDBObject bson = new BasicDBObject("userid", "1013");// 构建查询 条件
        ObjectId objectId = new ObjectId("12");
        collection.find(bson);
//        collection.
    }

}
