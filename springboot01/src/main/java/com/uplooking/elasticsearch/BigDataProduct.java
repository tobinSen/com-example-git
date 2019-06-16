package com.uplooking.elasticsearch;

public class BigDataProduct {

    private String name;
    private String content;
    private String version;

    public BigDataProduct(String name, String content, String version) {
        this.name = name;
        this.content = content;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
