package com.example.spring.elasticsearch.controller;

import com.example.spring.elasticsearch.dao.CityRepository;
import com.example.spring.elasticsearch.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping(value = "api/city.do")
    public City save(@RequestBody City city) throws Exception {
        System.out.println(city);
        return cityRepository.save(city);
    }

}
