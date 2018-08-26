package com.prapps.ved.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
    private int chapterNo;
    private String name;
    @JsonIgnore
    private Book book;
    private String landCode;
    private String headline;
    private String content;
    private List<Sutra> sutras;

    public int getChapterNo() { return chapterNo; }

    public void setChapterNo(int id) { this.chapterNo = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Book getBook() { return book; }

    public void setBook(Book book) { this.book = book; }

    public String getLandCode() { return landCode; }

    public void setLandCode(String landCode) { this.landCode = landCode; }

    public String getHeadline() { return headline; }

    public void setHeadline(String headline) { this.headline = headline; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public List<Sutra> getSutras() {
        if (null == sutras) {
            sutras = new ArrayList<>();
        }
        return sutras;
    }

    public void setSutras(List<Sutra> verses) {
        this.sutras = verses;
    }
}
