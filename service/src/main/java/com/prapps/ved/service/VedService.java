package com.prapps.ved.service;

import com.prapps.ved.dto.Book;
import com.prapps.ved.mapper.BookMapper;
import com.prapps.ved.persistence.BookEntity;
import com.prapps.ved.persistence.ChapterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.mapper.SutraMapper;
import com.prapps.ved.persistence.SutraEntity;
import com.prapps.ved.persistence.repo.BookRepo;
import com.prapps.ved.persistence.repo.SutraRepo;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service @Transactional(readOnly = true)
public class VedService {
	@Autowired private SutraRepo sutraRepo;
	@Autowired private BookRepo bookRepo;
	@Autowired SutraMapper sutraMapper;
	@Autowired BookMapper bookMapper;

	public Sutra getSutra(Long bookId, Integer chapterNo, Integer sutraNo, String script) {
		SutraEntity entity = new SutraEntity();
		//entity.setBook(bookRepo.findOne(bookId));
		entity.setSutraNo(sutraNo);
		entity.setLangCode(script);
		//entity.setChapterNo(chapterNo);
		Example<SutraEntity> example = Example.of(entity);
		return sutraMapper.map(sutraRepo.findOne(example));
	}

	public List<Sutra> getSutras(Long bookId, int chapterNo, String script, int startIndex, int size) {
		SutraEntity entity = new SutraEntity();
		//entity.setBook(bookRepo.findOne(bookId));
		entity.setLangCode(script);
		ChapterEntity chapter = new ChapterEntity();
		chapter.setId(chapterNo);
		entity.setChapter(chapter);
		Example<SutraEntity> example = Example.of(entity);
		final PageRequest page = new PageRequest(
				startIndex, size, Sort.Direction.ASC, "sutraNo"
		);
		return sutraMapper.map(sutraRepo.findBySutraNoBetween(bookId, chapterNo, "ro", startIndex, startIndex + size));
	}

	public List<Book> getBooks() {
		return bookMapper.map(bookRepo.findAll());
	}

	public Book getBookById(Long id, Integer chapterId, String script) {
		BookEntity example = new BookEntity();
		example.setId(id);
		ChapterEntity chapter = new ChapterEntity();
		chapter.setId(chapterId);
		example.setChapters(Collections.singletonList(chapter));
		Book book = bookMapper.map(bookRepo.findOne(Example.of(example)), Boolean.TRUE);
		List<Sutra> sutras = getSutras(book.getId(), chapter.getId(), script, 0, 10);
		book.getChapters().get(chapter.getId() - 1).setSutras( sutras );
		return book;
	}
}
