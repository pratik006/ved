package com.prapps.ved.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="language")
public class LanguageEntity {
    @Id
    private String code;
    private String name;

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }

    @Override
    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj instanceof ChapterIdEntity) {
            ChapterIdEntity other = (ChapterIdEntity) otherObj;
            return Objects.equals(code, name);
        }

        return false;
    }
}
