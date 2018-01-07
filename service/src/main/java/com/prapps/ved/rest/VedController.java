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

	private VedService vedService;

    @Autowired
    public VedController(VedService vedService) {
        this.vedService = vedService;
    }

    @RequestMapping("/books")
	public List<Book> getBooks() {
        return vedService.getBooks();
    }

	@RequestMapping("/book/{id}/{chapterId}")
	public Book getBook(@PathVariable Long id, @PathVariable Integer chapterId,
						@RequestParam(required = false, defaultValue = "ro") String script,
						@RequestParam(required = false, defaultValue = "1") int startIndex,
						@RequestParam(required = false, defaultValue = "10") int size) {
		return vedService.getBookById(id, chapterId, script, startIndex, size);
	}

	@RequestMapping("/book/{id}")
	public Book getBook(@PathVariable Long id, @RequestParam(required = false, defaultValue = "ro") String script,
						@RequestParam(required = false, defaultValue = "1") int startIndex,
						@RequestParam(required = false, defaultValue = "10") int size) {
		return vedService.getBookById(id, 1, script, startIndex, size);
	}
	
	@RequestMapping("/{bookId}/{chapterNo}/sutras")
	public List<Sutra> getSutra(@PathVariable Long bookId, @PathVariable int chapterNo,
						  @RequestParam(required = false, defaultValue = "ro") String script,
						  @RequestParam(required = false, defaultValue = "0") int startIndex,
						  @RequestParam(required = false, defaultValue = "10") int size
						  ) {
		long start = System.currentTimeMillis();
		List<Sutra> sutras = vedService.getSutras(bookId, chapterNo, script, startIndex, size);
		long end = System.currentTimeMillis();
		LOG.trace("Time: " + (end-start) + " miliseconds");
		return sutras;
	}
}
