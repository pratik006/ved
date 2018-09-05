package com.prapps.ved.service;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		Entity entity = new Entity(Book.KIND, code);
		try {
			entity = datastore.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new VedException(e);
		}
		
		Book book = new Book(String.valueOf(entity.getProperty(Book.NAME)));
		book.setCode(String.valueOf(entity.getProperty(Book.CODE)));
		book.setAuthorName(String.valueOf(entity.getProperty(Book.AUTHOR_NAME)));
		book.setPreviewUrl(String.valueOf(entity.getProperty(Book.PREVIEW_URL)));

		book.setAvailableLanguages(datastoreMapper.fromEntity(entity, Book.AVAILABLE_LANGUAGES, Language.class));	
		book.setAvailableCommentators((List<String>)entity.getProperty(Book.AVAILABLE_COMMENTATORS));	
		book.setSutras(datastoreMapper.fromEntity(entity, Book.SUTRAS, Sutra.class));

		return book;
	}
	
	public Book saveBook(Book book) throws VedException {
		Entity entity = new Entity(book.getKind(), book.getCode());
		entity.setProperty(Book.CODE, book.getCode());
		entity.setProperty(Book.NAME, book.getName());
		entity.setProperty(Book.AUTHOR_NAME, book.getAuthorName());
		entity.setProperty(Book.PREVIEW_URL, book.getPreviewUrl());

		entity.setProperty(Book.AVAILABLE_COMMENTATORS, book.getAvailableCommentators());
		entity.setProperty(Book.AVAILABLE_LANGUAGES, datastoreMapper.toEmbeddedEntity(book.getAvailableLanguages(), EmbeddedEntity.class));
		entity.setProperty(Book.SUTRAS, datastoreMapper.toEmbeddedEntity(book.getSutras(), EmbeddedEntity.class));

		Key key = datastore.put(entity);
		return findBook(key.getName());
	}
	
	public Book update(String code, Book newBook) throws VedException {
		Book existingBook = findBook(code);
		BeanUtils.copyProperties(newBook, existingBook);
		return saveBook(newBook);
	}
	
	public Book delete(String code) throws VedException {
		Entity entity = new Entity(Book.KIND, code);
		try {
			entity = datastore.get(entity.getKey());
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new VedException(e);
		}
		
		Book book = new Book(String.valueOf(entity.getProperty(Book.NAME)));
		book.setCode(String.valueOf(entity.getProperty(Book.CODE)));
		book.setAuthorName(String.valueOf(entity.getProperty(Book.AUTHOR_NAME)));
		book.setPreviewUrl(String.valueOf(entity.getProperty(Book.PREVIEW_URL)));

		datastore.delete(entity.getKey());
		
		return book;
	}
}
