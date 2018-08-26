package com.prapps.ved;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.prapps.ved.dto.Book;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

public class BookServiceIntegTest {
  private static final String BASE_APP_URI = "https://vedsangraha-187514.appspot.com/";
  private RestTemplate restTemplate = new RestTemplate();

  @Test
  public void testCrudOp() throws Exception {
    Book book = new Book("testing name");
    book.setCode("testing");
    Book savedBook = restTemplate.postForEntity(BASE_APP_URI+"/books", book, Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());

    savedBook = restTemplate.getForEntity(BASE_APP_URI+"/books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());

    book.setName("new name");
    restTemplate.put(BASE_APP_URI+"/books/"+book.getCode(), book);
    savedBook = restTemplate.getForEntity(BASE_APP_URI+"/books/"+book.getCode(), Book.class).getBody();
    Assert.assertEquals(book.getCode(), savedBook.getCode());
    Assert.assertEquals(book.getName(), savedBook.getName());

    restTemplate.delete(BASE_APP_URI+"/books/"+book.getCode());
  }
}
