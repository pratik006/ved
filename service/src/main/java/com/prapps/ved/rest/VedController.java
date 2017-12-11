package com.prapps.ved.rest;

import com.prapps.ved.dto.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.service.VedService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class VedController {
	private final Logger LOG = LoggerFactory.getLogger(VedController.class);

	@Autowired VedService vedService;

    @RequestMapping("/books")
	public List<Book> getBooks() {
        return vedService.getBooks();
    }

	@RequestMapping("/book/{id}")
	public Book getBook(@PathVariable Long id) {
		return vedService.getBookById(id);
	}
	
	@RequestMapping("/{bookId}/{chapterNo}/{sutraNo}")
	public Sutra getSutra(@PathVariable Long bookId,@PathVariable Integer chapterNo, @PathVariable Integer sutraNo,
						  @RequestParam(required = false, defaultValue = "ro") String script) {
		long start = System.currentTimeMillis();
		Sutra sutra = vedService.getSutra(bookId, chapterNo, sutraNo, script);
		long end = System.currentTimeMillis();
		LOG.trace("Time: " + (end-start) + " miliseconds");
		return sutra;
	}
}
