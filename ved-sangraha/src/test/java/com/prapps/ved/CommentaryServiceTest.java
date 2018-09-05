package com.prapps.ved;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.CommentarySearchRequest;
import com.prapps.ved.service.CommentaryService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentaryServiceTest {

    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Autowired
    CommentaryService service;

    @Before
    public void setup() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testSave() {
        Commentary commentary = new Commentary();
        commentary.setSutraNo(1L);
        commentary.setChapterNo(1L);
        commentary.setContent("test123");
        commentary.setCommentator("abc");
        commentary.setLanguage("dv");
        List<String> keys = service.save(Collections.singletonList(commentary));
        System.out.println(keys);

        CommentarySearchRequest req = new CommentarySearchRequest();
        req.setChapterNo(1);
        req.setLanguage("dv");
        req.setSutraNo(1);
        List<Commentary> result = service.findCommentary(req);
        Assert.assertNotNull(result.stream().findFirst().get().getSutraNo());
        Assert.assertNotNull(result.stream().findFirst().get().getChapterNo());
        Assert.assertNotNull(result.stream().findFirst().get().getLanguage());
    }
}
