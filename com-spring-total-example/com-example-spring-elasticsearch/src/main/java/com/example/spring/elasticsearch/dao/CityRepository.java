package com.example.spring.elasticsearch.dao;

import com.example.spring.elasticsearch.domain.City;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CityRepository extends ElasticsearchRepository<City, Long> {
}
