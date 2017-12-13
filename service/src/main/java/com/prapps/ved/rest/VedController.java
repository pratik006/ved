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
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class VedController {
	private final Logger LOG = LoggerFactory.getLogger(VedController.class);

	@Autowired VedService vedService;

    @RequestMapping("/books")
	public List<Book> getBooks() {
        return vedService.getBooks();
    }

	@RequestMapping("/books/{id}")
	public Book getBook(@PathVariable Long id) {
		return vedService.getBookById(id);
	}
	
	@RequestMapping("/{bookId}/{chapterNo}/sutras")
	public Set<Sutra> getSutra(@PathVariable Long bookId, @PathVariable int chapterNo,
						  @RequestParam(required = false, defaultValue = "ro") String script,
						  @RequestParam(required = false, defaultValue = "0") int startIndex,
						  @RequestParam(required = false, defaultValue = "10") int size
						  ) {
		long start = System.currentTimeMillis();
		Set<Sutra> sutras = vedService.getSutras(bookId, chapterNo, script, startIndex, size);
		long end = System.currentTimeMillis();
		LOG.trace("Time: " + (end-start) + " miliseconds");
		return sutras;
	}
}
