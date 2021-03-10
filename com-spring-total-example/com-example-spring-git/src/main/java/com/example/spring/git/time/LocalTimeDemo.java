package com.example.spring.git.time;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * LocalDate
 * LocalTime
 * LocalDateTime
 * Instant
 * Duration
 * Period
 */
public class LocalTimeDemo {

    @Test
    public void instance() {
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.now(ZoneId.systemDefault()));
        System.out.println(LocalDateTime.now(Clock.systemDefaultZone()));
        System.out.println(LocalDateTime.MIN);
        System.out.println(LocalDateTime.MAX);

        System.out.println(LocalDateTime.of(2020, 12, 2, 23, 21));

        System.out.println(ChronoUnit.HOURS.between(LocalDateTime.now(), LocalDateTime.now()));

        System.out.println(LocalDateTime.now().plusDays(1));

        // date <->Instance

        Instant now = Instant.now();
        Date date = Date.from(now);
        System.out.println(date);
        System.out.println(date.toInstant());

        // date <-> LocalDate

        System.out.println(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        // date <-> LocalTime
        LocalDateTime time = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        System.out.println(Date.from(time.atZone(ZoneId.systemDefault()).toInstant()));

        System.out.println(LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault()).toLocalTime());

        // date <-> LocalDateTime
        System.out.println(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        // Duration
        long seconds = Duration.between(LocalDateTime.now(), LocalDateTime.now().plusDays(1)).get(ChronoUnit.SECONDS);

        // Period
        long days = Period.between(LocalDate.now(), LocalDate.now().plusDays(1)).get(ChronoUnit.DAYS);

        System.out.println(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

    }
}
