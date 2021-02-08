package com.example.spring.mybatis.convert;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;


@Slf4j
@Component
public class CustomMovieEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        Movie movie = (Movie) getValue();
        return movie == null ? "" : movie.getName();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        log.info("继承[PropertyEditorSupport]类,转换数据={}", text);
        String[] data = text.split("-");
        Movie movie = Movie.builder().name(data[0]
                .toUpperCase()).seat(Integer.parseInt(data[1]))
                .build();
        setValue(movie);
    }
}
