package com.prapps.ved;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;

@Service
public class BookService {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public Book findBook(String code) throws VedException {
		Entity entity = new Entity("book", code);
		try {
			entity = datastore.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new VedException(e);
		}
		
		Book book = new Book();
		book.setCode(String.valueOf(entity.getProperty("code")));
		book.setName(String.valueOf(entity.getProperty("name")));
		return book;
	}
	
	public Book saveBook(Book book) throws VedException {
		Entity entity = new Entity("book", book.getCode());
		entity.setProperty("code", book.getCode());
		entity.setProperty("name", book.getName());
		Key key = datastore.put(entity);
		return findBook(key.getName());
	}
	
	public Book update(String code, Book newBook) throws VedException {
		Book existingBook = findBook(code);
		BeanUtils.copyProperties(newBook, existingBook);
		return saveBook(newBook);
	}
	
	public Book delete(String code) throws VedException {
		Entity entity = new Entity("book", code);
		try {
			entity = datastore.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new VedException(e);
		}
		
		Book book = new Book();
		book.setCode(String.valueOf(entity.getProperty("code")));
		book.setName(String.valueOf(entity.getProperty("name")));
		
		datastore.delete(entity.getKey());
		
		return book;
	}
}
