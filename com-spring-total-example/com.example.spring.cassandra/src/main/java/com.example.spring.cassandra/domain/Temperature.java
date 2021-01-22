package com.example.spring.cassandra.domain;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Table("temperature")
public class Temperature {

    @PrimaryKeyColumn(value = "weatherstation_ip", type = PrimaryKeyType.PARTITIONED)
    private String weatherstation_ip;
    @PrimaryKeyColumn(value = "event_time", type = PrimaryKeyType.CLUSTERED)
    private Timestamp event_time;
    @Column("value")
    private double value;

    public String getWeatherstation_ip() {
        return weatherstation_ip;
    }

    public void setWeatherstation_ip(String weatherstation_ip) {
        this.weatherstation_ip = weatherstation_ip;
    }

    public Timestamp getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Timestamp event_time) {
        this.event_time = event_time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double temperature) {
        this.value = temperature;
    }
}

