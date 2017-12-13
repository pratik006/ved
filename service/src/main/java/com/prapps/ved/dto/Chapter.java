package com.prapps.ved.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class Chapter {
    private int id;
    private String name;
    @JsonIgnore
    private Book book;
    private String landCode;
    private String headline;
    private String content;
    private Set<Sutra> sutras;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public Set<Sutra> getVerses() {
        if (null == sutras) {
            sutras = new HashSet<>();
        }
        return sutras;
    }

    public void setVerses(Set<Sutra> verses) {
        this.sutras = verses;
    }
}
