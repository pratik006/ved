package com.prapps.ved.persistence;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapter")
@NamedEntityGraph(name = "ChapterEntity.detail", attributeNodes = @NamedAttributeNode("sutras"))
public class ChapterEntity {
    @EmbeddedId
    private ChapterIdEntity id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "book_id", updatable = false, insertable = false)
    private BookEntity book;
    private String langCode;
    private String headline;
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter", fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<SutraEntity> sutras;

    public ChapterIdEntity getId() { return id; }
    public void setId(ChapterIdEntity chapterId) { this.id = chapterId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BookEntity getBook() { return book; }
    public void setBook(BookEntity book) { this.book = book; }

    public String getLangCode() { return langCode; }
    public void setLangCode(String langCode) { this.langCode = langCode; }

    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public List<SutraEntity> getSutras() {
        if (null == sutras) {
            sutras = new ArrayList<>();
        }
        return sutras;
    }
    public void setSutras(List<SutraEntity> verses) {
        this.sutras = verses;
    }
}
