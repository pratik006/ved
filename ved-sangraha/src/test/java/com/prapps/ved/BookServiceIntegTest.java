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
    book.setAvailableCommentators(Arrays.asList("aaa", "bbb"));
    Sutra sutra = new Sutra();
    sutra.setChapterNo(1L);
    sutra.setSutraNo(1L);
    sutra.setContent("testing");
    Sutra sutra2 = new Sutra();
    sutra2.setChapterNo(1L);
    sutra2.setSutraNo(1L);
    sutra2.setContent("testing");
    book.setSutras(Arrays.asList(sutra, sutra2));

    final Book savedBook = restTemplate.postForEntity(BASE_APP_URI + "books", book, Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook.getPreviewUrl());
    Assert.assertEquals(book.getAvailableLanguages().size(), savedBook.getAvailableLanguages().size());
    Assert.assertEquals(book.getAvailableCommentators().size(), savedBook.getAvailableCommentators().size());
    book.getAvailableCommentators().stream().forEach(c1 -> savedBook.getAvailableCommentators().stream().filter(c2->c2.equals(c1)));
    book.getAvailableLanguages().stream().forEach(c1 -> savedBook.getAvailableLanguages().stream().filter(c2->c2.getCode().equals(c1.getCode()) && c2.getName().equals(c2.getName())));

    Assert.assertEquals(book.getSutras().size(), savedBook.getSutras().size());

    Book savedBook2 = restTemplate.getForEntity(BASE_APP_URI+"books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook2.getCode());
    Assert.assertEquals(book.getName(), savedBook2.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook2.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook2.getPreviewUrl());

    book.setName("new name");
    restTemplate.put(BASE_APP_URI+"/books/"+book.getCode(), book);
    savedBook2 = restTemplate.getForEntity(BASE_APP_URI+"books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook2.getCode());
    Assert.assertEquals(book.getName(), savedBook2.getName());
    Assert.assertEquals(book.getAuthorName(), savedBook2.getAuthorName());
    Assert.assertEquals(book.getPreviewUrl(), savedBook2.getPreviewUrl());

    restTemplate.delete(BASE_APP_URI+"/books/"+book.getCode());
  }
}
