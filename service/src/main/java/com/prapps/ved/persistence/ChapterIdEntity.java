package com.prapps.ved.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ChapterIdEntity implements Serializable {
    @Column(name = "book_id")
    private Long bookId;
    private int chapterNo;

    public ChapterIdEntity() { }

    public ChapterIdEntity(Long bookId, int chapterNo) {
        this.bookId = bookId;
        this.chapterNo = chapterNo;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public int hashCode() {
        return Objects.hash(bookId, chapterNo);
    }

    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj instanceof ChapterIdEntity) {
            ChapterIdEntity other = (ChapterIdEntity) otherObj;
            return Objects.equals(bookId, chapterNo);
        }

        return false;
    }
}
