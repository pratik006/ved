package com.prapps.ved;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.Language;
import com.prapps.ved.dto.Sutra;
import com.prapps.ved.service.BookService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    BookService service;

    @Before
    public void setup() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSave() throws VedException {
        Book book = createDummyBook();
        final Book savedBook = service.saveBook(book);
        validateBook(book, savedBook);

        Book savedBook2 = service.saveBook(book);
        validateBook(book, savedBook2);

        book.setName("new name");
        savedBook2 = service.saveBook(book);
        validateBook(book, savedBook2);

        service.delete(book.getCode());
    }

    @Test
    public void testFindBook() throws VedException {
        Book book = createDummyBook();
        final Book savedBook = service.saveBook(book);
        Book searchedBook = service.findBook(savedBook.getCode());
        validateBook(book, searchedBook);
    }

    @Test(expected = VedException.class)
    public void testDeleteBook() throws VedException {
        Book book = createDummyBook();
        final Book savedBook = service.saveBook(book);
        service.delete(savedBook.getCode());
        Book searchedBook = service.findBook(savedBook.getCode());
    }

    private void validateBook(Book expected, Book actual) {
        Assert.assertEquals(expected.getCode(), actual.getCode());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getAuthorName(), actual.getAuthorName());
        Assert.assertEquals(expected.getPreviewUrl(), actual.getPreviewUrl());
        Assert.assertEquals(expected.getAvailableLanguages().size(), actual.getAvailableLanguages().size());
        Assert.assertEquals(expected.getAvailableCommentators().size(), actual.getAvailableCommentators().size());
        expected.getAvailableCommentators().stream().forEach(c1 -> actual.getAvailableCommentators().stream().filter(c2->c2.equals(c1)));
        expected.getAvailableLanguages().stream().forEach(c1 -> actual.getAvailableLanguages().stream().filter(c2->c2.getCode().equals(c1.getCode()) && c2.getName().equals(c2.getName())));

        Assert.assertEquals(expected.getSutras().size(), actual.getSutras().size());
        Assert.assertTrue(expected.getSutras().equals(actual.getSutras()));
    }

    private Book createDummyBook() {
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
        sutra2.setSutraNo(2L);
        sutra2.setContent("testing");

        Commentary commentary = new Commentary();
        commentary.setSutraNo(1L);
        commentary.setChapterNo(1L);
        commentary.setContent("test commentary");
        commentary.setLanguage("dv");
        commentary.setCommentator("test commentator");
        sutra.getCommentaries().add(commentary);

        commentary = new Commentary();
        commentary.setSutraNo(2L);
        commentary.setChapterNo(1L);
        commentary.setContent("test commentary2");
        commentary.setLanguage("dv");
        commentary.setCommentator("test commentator");
        sutra.getCommentaries().add(commentary);

        book.setSutras(Arrays.asList(sutra, sutra2));
        return book;
    }
}
