package com.prapps.ved.service;

import com.google.appengine.api.datastore.*;
import com.prapps.ved.dto.Book;
import com.prapps.ved.VedException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public List<Book> getAllBooks() {
		Query q = new Query(Entities.PROPERTY_METADATA_KIND).setKeysOnly();

		List<Book> books = new ArrayList<>();
		for (Entity e : datastore.prepare(q).asIterable()) {
			Book book = new Book(e.getKey().getName());
			book.setCode(e.getKey().getKind());
			books.add(book);
		}

		return books;
	}

	public Book findBook(String code) throws VedException {
		Entity entity = new Entity("book", code);
		try {
			entity = datastore.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new VedException(e);
		}
		
		Book book = new Book(String.valueOf(entity.getProperty("name")));
		book.setCode(String.valueOf(entity.getProperty("code")));
		book.setAuthorName(String.valueOf(entity.getProperty("authorName")));
		book.setPreviewUrl(String.valueOf(entity.getProperty("previewUrl")));

		return book;
	}
	
	public Book saveBook(Book book) throws VedException {
		Entity entity = new Entity("book", book.getCode());
		entity.setProperty("code", book.getCode());
		entity.setProperty("name", book.getName());
		entity.setProperty("authorName", book.getCode());
		entity.setProperty("previewUrl", book.getName());
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
		
		Book book = new Book(String.valueOf(entity.getProperty("name")));
		book.setCode(String.valueOf(entity.getProperty("code")));
		book.setAuthorName(String.valueOf(entity.getProperty("authorName")));
		book.setPreviewUrl(String.valueOf(entity.getProperty("previewUrl")));

		datastore.delete(entity.getKey());
		
		return book;
	}
}
