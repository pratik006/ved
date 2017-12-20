package com.prapps.ved;

import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Chapter;
import com.prapps.ved.service.VedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
@ActiveProfiles("local")
public class TestConfigLoader {
    @Autowired VedService service;
    private static final String DEFAULT_SCRIPT = "ro";

    @Test public void testLoadConfiguration() { }

    @Test public void testGetBooks() {
        assertTrue(!service.getBooks().isEmpty());
    }

    @Test public void testGetBookById() {
        int chapterNo = 1;
        Book book = service.getBookById(1l, chapterNo, DEFAULT_SCRIPT);
        assertTrue(!book.getChapters().isEmpty());
        Chapter chapter = book.getChapters().get(chapterNo - 1);
        assertEquals("ARJUN-VISHAD", chapter.getName());
        assertTrue(!chapter.getSutras().isEmpty());
    }

    @Test public void testGetSutras() {
        assertEquals(10, service.getSutras(1L, 1, "ro", 0, 10).size());
    }
}