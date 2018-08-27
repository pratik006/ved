package com.prapps.ved;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Language;
import com.prapps.ved.dto.Sutra;

public class BookServiceIntegTest {
  //private static final String BASE_APP_URI = "https://vedsangraha-187514.appspot.com/";
  private static final String BASE_APP_URI = "http://localhost:8080/";
  private RestTemplate restTemplate = new RestTemplate();

  @Test
  public void testCrudOp() throws Exception {
    Book book = new Book("testing name");
    book.setCode("testing");
    book.setAuthorName("test author");
    book.setPreviewUrl("previewurl");
    book.setAvailableLanguages(Arrays.asList(
            new Language("ro", "English"),
            new Language("dv", "Hindi")));
    Sutra sutra = new Sutra();
    sutra.setChapterNo(1);
    sutra.setSutraNo(1);
    sutra.setContent("testing");
    Sutra sutra2 = new Sutra();
    sutra2.setChapterNo(1);
    sutra2.setSutraNo(1);
    sutra2.setContent("testing");
    book.setSutras(Arrays.asList(sutra, sutra2));

    Book savedBook = restTemplate.postForEntity(BASE_APP_URI + "books", book, Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook.getPreviewUrl());
    Assert.assertEquals(book.getAvailableLanguages().size(), savedBook.getAvailableLanguages().size());
    Assert.assertEquals(book.getSutras().size(), savedBook.getSutras().size());

    savedBook = restTemplate.getForEntity(BASE_APP_URI+"books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook.getPreviewUrl());

    book.setName("new name");
    restTemplate.put(BASE_APP_URI+"/books/"+book.getCode(), book);
    savedBook = restTemplate.getForEntity(BASE_APP_URI+"books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook.getPreviewUrl());

    restTemplate.delete(BASE_APP_URI+"/books/"+book.getCode());
  }
}
