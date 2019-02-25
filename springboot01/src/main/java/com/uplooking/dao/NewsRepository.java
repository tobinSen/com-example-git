package com.uplooking.dao;

import com.uplooking.pojo.New;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface NewsRepository extends ElasticsearchRepository<New, Long> {

    public List<New> findByTitle(String title);
}
