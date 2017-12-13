package com.prapps.ved.persistence;

import com.prapps.ved.persistence.BookEntity;
import com.prapps.ved.persistence.SutraEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "chapter")
@NamedEntityGraph(name = "ChapterEntity.detail", attributeNodes = @NamedAttributeNode("sutras"))
public class ChapterEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @Column(name="book_id", insertable = false, updatable = false)
    private Long bookId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;
    private String langCode;
    private String headline;
    private String content;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter", fetch = FetchType.LAZY)
    @OrderBy("id asc")
    private List<SutraEntity> sutras;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

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
