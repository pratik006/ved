
package com.prapps.ved.mapper;

import com.prapps.ved.dto.Book;
import com.prapps.ved.persistence.BookEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BookMapper {
    @Autowired SutraMapper sutraMapper;
    @Autowired ChapterMapper chapterMapper;

    public Book map(BookEntity entity, boolean detail) {
        Book book = new Book(entity.getName());
        BeanUtils.copyProperties(entity, book);
        book.setChapters(detail?chapterMapper.map(entity.getChapters()):null);
        return book;
    }

    public List<Book> map(Collection<BookEntity> entities) {
        List<Book> books = new ArrayList<>();
        entities.forEach(entity->books.add(map(entity, false)));
        return books;
    }
}
