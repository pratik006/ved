package com.prapps.ved.mapper;

import com.prapps.ved.dto.Book;
import com.prapps.ved.persistence.BookEntity;
import com.prapps.ved.persistence.SutraEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BookMapper {
    @Autowired SutraMapper sutraMapper;

    public Book map(BookEntity entity) {
        Book book = new Book(entity.getName());
        BeanUtils.copyProperties(entity, book);
        List<SutraEntity> sublist = new ArrayList<>(10);
        entity.getVerses().forEach(sutra -> {
            if (sublist.size() < 10) {
                sublist.add(sutra);
            }
        });
        book.setVerses(sutraMapper.map(sublist));
        return book;
    }

    public List<Book> map(Collection<BookEntity> entities) {
        List<Book> books = new ArrayList<>();
        for (BookEntity entity : entities) {
            books.add(map(entity));
        }

        return books;
    }
}
