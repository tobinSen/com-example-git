package com.example.spring.cassandra.service;

import com.example.spring.cassandra.domain.Temperature;

import java.util.List;

public interface EnvService {

    Temperature save(Temperature weather);

    List<Temperature> query(Temperature temperature);

    boolean delete(Temperature temperature);

    Temperature selectOne(Temperature temperature);
}
