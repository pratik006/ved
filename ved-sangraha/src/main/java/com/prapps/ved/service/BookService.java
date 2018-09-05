package com.prapps.ved.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entities;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.prapps.ved.VedException;
import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Language;
import com.prapps.ved.dto.Sutra;

@Service
public class BookService {
	@Autowired
	private DatastoreService datastore;
	@Autowired
	private DatastoreEntityMapper datastoreMapper;

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

		book.setAvailableLanguages(datastoreMapper.fromEntity(entity, Book.AVAILABLE_LANGUAGES, Language.class));	
		book.setAvailableCommentators((List<String>)entity.getProperty(Book.AVAILABLE_COMMENTATORS));	
		book.setSutras(datastoreMapper.fromEntity(entity, Book.SUTRAS, Sutra.class));

		return book;
	}
	
	public Book saveBook(Book book) throws VedException {
		Entity entity = new Entity("book", book.getCode());
		entity.setProperty("code", book.getCode());
		entity.setProperty("name", book.getName());
		entity.setProperty("authorName", book.getAuthorName());
		entity.setProperty("previewUrl", book.getPreviewUrl());

		entity.setProperty(Book.AVAILABLE_COMMENTATORS, book.getAvailableCommentators());
		entity.setProperty(Book.AVAILABLE_LANGUAGES, datastoreMapper.toEmbeddedEntity(book.getAvailableLanguages()));
		entity.setProperty(Book.SUTRAS, datastoreMapper.toEmbeddedEntity(book.getSutras()));

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
