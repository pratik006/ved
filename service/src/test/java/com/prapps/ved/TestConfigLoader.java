package com.prapps.ved;

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
        assertTrue(!service.getBookById(1L, 1, DEFAULT_SCRIPT).getChapters().isEmpty());
    }

    @Test public void testGetSutras() {
        assertEquals(10, service.getSutras(1L, 1, "ro", 0, 10).size());
    }
}