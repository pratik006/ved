package com.prapps.ved.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prapps.ved.Book;
import com.prapps.ved.BookService;
import com.prapps.ved.VedException;

@RestController
@RequestMapping("/books")
public class VedController {
	@Autowired BookService service;
	
	@RequestMapping(value="/{code}", method= {RequestMethod.GET})
	public Book find(@PathVariable("code") String code) throws VedException {		
		return service.findBook(code);
	}
	
	@RequestMapping(method= {RequestMethod.POST})
	public Book save(Book book) throws VedException {
		return service.saveBook(book);
	}
	
	@RequestMapping(value="/{code}", method= {RequestMethod.PUT})
	public Book update(@PathVariable("code") String code, @RequestBody Book book) throws VedException {
		return service.saveBook(book);
	}
	
	@RequestMapping(value="/{code}", method= {RequestMethod.DELETE})
	public Book update(@PathVariable("code") String code) throws VedException {
		return service.delete(code);
	}
}
