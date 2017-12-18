package com.prapps.ved;

import com.prapps.ved.service.VedService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {ApplicationStarter.class})
@ActiveProfiles("local")
public class TestConfigLoader {
    @Autowired VedService service;

    @Test public void testLoadConfiguration() {

    }
}