package com.example.spring.elasticsearch.controller;

import com.example.spring.elasticsearch.dao.CityRepository;
import com.example.spring.elasticsearch.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping(value = "api/city.do")
    public void save(@RequestBody City city) throws Exception {

        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            City c = new City();
            c.setId(Long.valueOf(i));
            c.setName("tongjian" + i);
            c.setScore("29" + new Random().nextInt(1999));
            c.setDescription("kibana " + i + "opertation");
            c.setTimestamp(new Date());
            cityList.add(c);
        }
        cityRepository.saveAll(cityList);
    }

    @RequestMapping("jmeter.do")
    public String jmeter(String name) throws Exception {
       Thread.sleep(100000);
        System.out.println("jmeter");
        return new Random().nextInt(19) + name;
    }

}
