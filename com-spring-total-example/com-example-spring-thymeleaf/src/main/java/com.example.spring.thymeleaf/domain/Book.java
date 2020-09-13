package com.example.spring.thymeleaf.domain;

import java.io.Serializable;

public class Book implements Serializable {

    private String btitle;
    private String bauthor;

    public String getBtitle() {
        return btitle;
    }

    public void setBtitle(String btitle) {
        this.btitle = btitle;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public Book() {
        super();
    }

    public Book(String btitle, String bauthor) {
        super();
        this.btitle = btitle;
        this.bauthor = bauthor;
    }

}
