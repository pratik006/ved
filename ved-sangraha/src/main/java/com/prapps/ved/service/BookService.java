package com.prapps.ved.service;

import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.org.apache.commons.codec.language.bm.Lang;
import com.prapps.ved.dto.Book;
import com.prapps.ved.VedException;
import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.Language;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
	//private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	@Autowired
	private DatastoreService datastore;

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

		if (entity.hasProperty(Book.LANGUAGES)) {
			List<EmbeddedEntity> languagesEntities = (List<EmbeddedEntity>) entity.getProperty(Book.LANGUAGES);
			book.setAvailableLanguages(
					languagesEntities.stream().map(lang -> new Language(
							String.valueOf(lang.getProperty(Language.CODE)),
							String.valueOf(lang.getProperty(Language.NAME)))
					).collect(Collectors.toList())
			);	
		}
		
		if (entity.hasProperty(Book.COMMENTARIES)) {
			book.setAvailableCommentaries(
					((List<EmbeddedEntity>) entity.getProperty(Book.COMMENTARIES)).stream().map(comm -> {
						Commentary commentary = new Commentary();
						commentary.setSutraNo((Integer)comm.getProperty(Commentary.SUTRA_NO));
						commentary.setChapterNo((Integer)comm.getProperty(Commentary.CHAPTER_NO));
						commentary.setContent(String.valueOf(comm.getProperty(Commentary.CONTENT)));
						commentary.setCommentator(String.valueOf(comm.getProperty(Commentary.COMMENTATOR)));
						commentary.setLanguage(String.valueOf(comm.getProperty(Commentary.LANGUAGE)));
						return commentary;
					}).collect(Collectors.toList())
			);	
		}

		return book;
	}
	
	public Book saveBook(Book book) throws VedException {
		Entity entity = new Entity("book", book.getCode());
		entity.setProperty("code", book.getCode());
		entity.setProperty("name", book.getName());
		entity.setProperty("authorName", book.getAuthorName());
		entity.setProperty("previewUrl", book.getPreviewUrl());

		if (book.getAvailableCommentaries() != null) {
			List<EmbeddedEntity> commentariesEntity = book.getAvailableCommentaries().stream().map(comm -> {
				EmbeddedEntity commentary = new EmbeddedEntity();
				commentary.setProperty(Commentary.CHAPTER_NO, comm.getChapterNo());
				commentary.setProperty(Commentary.SUTRA_NO, comm.getSutraNo());
				commentary.setProperty(Commentary.COMMENTATOR, comm.getCommentator());
				commentary.setProperty(Commentary.CONTENT, comm.getContent());
				commentary.setProperty(Commentary.LANGUAGE, comm.getLanguage());
				return commentary;
			}).collect(Collectors.toList());
			entity.setProperty(Book.COMMENTARIES, commentariesEntity);
		}

		if (book.getAvailableLanguages() != null) {
			List<EmbeddedEntity> languagesEntity = book.getAvailableLanguages().stream().map(lang -> {
				EmbeddedEntity langEntity = new EmbeddedEntity();
				langEntity.setProperty(Language.CODE, lang.getCode());
				langEntity.setProperty(Language.NAME, lang.getName());
				return langEntity;
			}).collect(Collectors.toList());
			entity.setProperty(Book.LANGUAGES, languagesEntity);
		}

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
