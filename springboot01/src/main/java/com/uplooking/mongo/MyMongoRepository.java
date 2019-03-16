package com.uplooking.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MyMongoRepository extends MongoRepository<Book,Long> {
}
