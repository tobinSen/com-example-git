package com.example.spring.cassandra.service;

import com.example.spring.cassandra.domain.Temperature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvServiceImpl implements EnvService {

    @Autowired
    private CassandraTemplate cassandraTemplate;


    @Override
    public Temperature save(Temperature temperature) {
        cassandraTemplate.insert(temperature);
        return temperature;
    }

    @Override
    public List<Temperature> query(Temperature temperature) { // 命令空间 namespace
        return cassandraTemplate.select("select * from czenv.temperature where weatherstation_ip = '" + temperature.getWeatherstation_ip() + "';", Temperature.class);
    }

    @Override
    public boolean delete(Temperature temperature) {
//        String queryStr="DELETE FROM czenv.temperature where weatherstation_ip = '" + temperature.getWeatherstation_ip() + "' and event_time = "+temperature.getEvent_time()+";";
        return cassandraTemplate.delete(Query.query(Criteria.where("weatherstation_ip").is(temperature.getWeatherstation_ip())).and(Criteria.where("event_time").lte(temperature.getEvent_time())), Temperature.class);
    }

    @Override
    public Temperature selectOne(Temperature temperature) {
        cassandraTemplate.selectOne(Query.query(Criteria.where("weatherstation_ip").is(temperature.getWeatherstation_ip())).limit(1), Temperature.class);
        return null;
    }
}
