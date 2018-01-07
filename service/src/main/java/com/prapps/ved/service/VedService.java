package com.prapps.ved.service;

import com.prapps.ved.dto.Book;
import com.prapps.ved.dto.Chapter;
import com.prapps.ved.dto.Language;
import com.prapps.ved.mapper.BookMapper;
import com.prapps.ved.mapper.ChapterMapper;
import com.prapps.ved.mapper.LanguageMapper;
import com.prapps.ved.persistence.*;
import com.prapps.ved.persistence.repo.ChapterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.mapper.SutraMapper;
import com.prapps.ved.persistence.repo.BookRepo;
import com.prapps.ved.persistence.repo.SutraRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service @Transactional(readOnly = true)
public class VedService {
	@Autowired private SutraRepo sutraRepo;
	@Autowired private BookRepo bookRepo;
	@Autowired private ChapterRepo chapterRepo;
	@Autowired SutraMapper sutraMapper;
	@Autowired BookMapper bookMapper;
	@Autowired ChapterMapper chapterMapper;
	@Autowired LanguageMapper languageMapper;

	public Sutra getSutra(Long bookId, Integer chapterNo, Integer sutraNo, String script) {
		SutraEntity entity = new SutraEntity();
		//entity.setBook(bookRepo.findOne(bookId));
		entity.setSutraNo(sutraNo);
		LanguageEntity languageEntity = new LanguageEntity();
		languageEntity.setCode(script);
		entity.setLanguage(languageEntity);
		//entity.setChapterNo(chapterNo);
		Example<SutraEntity> example = Example.of(entity);
		return sutraMapper.map(sutraRepo.findOne(example));
	}

	public List<Sutra> getSutras(Long bookId, int chapterNo, String script, int startIndex, int size) {
		return sutraMapper.map(
				sutraRepo.findBySutraNoBetween(bookId, chapterNo, script, startIndex, startIndex + size - 1));
	}

	public List<Book> getBooks() {
		return bookMapper.map(bookRepo.findAll());
	}

	public Book getBookById(Long id, Integer chapterNo, String script, int startIndex, int size) {
		BookEntity example = new BookEntity();
		example.setId(id);
		ChapterEntity chapter = new ChapterEntity();
		ChapterIdEntity chapterIdEntity = new ChapterIdEntity(id, chapterNo);
		chapter.setId(chapterIdEntity);
		example.setChapters(Collections.singletonList(chapter));
		Book book = bookMapper.map(bookRepo.findOne(Example.of(example)), Boolean.TRUE);
		book.getChapters().get(chapterNo-1).setSutras( getSutras(book.getId(), chapterNo, script, startIndex, size) );
		book.setAvailableLanguages(getAvailableLanguages(id));
		return book;
	}

	public List<Language> getAvailableLanguages(Long bookId) {
		return languageMapper.map(sutraRepo.getAvailableScripts(bookId));
	}

	public Chapter getChapter(Long bookId, Integer chapterNo) {
		ChapterIdEntity id = new ChapterIdEntity(bookId, chapterNo);
		return chapterMapper.map(chapterRepo.findOne(id));
	}
}
