package com.example.spring.pay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "settings")
@Configuration
public class CalendarSettings {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Map<LocalDate, LocalDate> calendar = new HashMap<>();

    public Map<LocalDate, LocalDate> getCalendar() {
        return calendar;
    }

    public void setCalendar(
            Map<LocalDate, LocalDate> calendar) {
        this.calendar = calendar;
    }
}
