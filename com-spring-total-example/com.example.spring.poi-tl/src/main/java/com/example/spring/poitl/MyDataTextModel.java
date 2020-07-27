package com.example.spring.poitl;

import com.deepoove.poi.data.HyperLinkTextRenderData;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.TextRenderData;

public class MyDataTextModel {

    private String name;
    private TextRenderData author;
    private HyperLinkTextRenderData link;

    private PictureRenderData img;

    private MiniTableRenderData table;

    public MyDataTextModel(String name, TextRenderData author, HyperLinkTextRenderData link, PictureRenderData img, MiniTableRenderData table) {
        this.name = name;
        this.author = author;
        this.link = link;
        this.img = img;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TextRenderData getAuthor() {
        return author;
    }

    public void setAuthor(TextRenderData author) {
        this.author = author;
    }

    public HyperLinkTextRenderData getLink() {
        return link;
    }

    public void setLink(HyperLinkTextRenderData link) {
        this.link = link;
    }

    public PictureRenderData getImg() {
        return img;
    }

    public void setImg(PictureRenderData img) {
        this.img = img;
    }

    public MiniTableRenderData getTable() {
        return table;
    }

    public void setTable(MiniTableRenderData table) {
        this.table = table;
    }
}
