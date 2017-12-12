package com.prapps.ved.service;

import com.prapps.ved.dto.Book;
import com.prapps.ved.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.mapper.SutraMapper;
import com.prapps.ved.persistence.SutraEntity;
import com.prapps.ved.persistence.repo.BookRepo;
import com.prapps.ved.persistence.repo.SutraRepo;

import java.util.List;

@Service
public class VedService {
	@Autowired private SutraRepo sutraRepo;
	@Autowired private BookRepo bookRepo;
	@Autowired SutraMapper sutraMapper;
	@Autowired BookMapper bookMapper;

	public Sutra getSutra(Long bookId, Integer chapterNo, Integer sutraNo, String script) {
		SutraEntity entity = new SutraEntity();
		entity.setBook(bookRepo.findOne(bookId));
		entity.setVerseNo(sutraNo);
		entity.setLangCode(script);
		entity.setChapterNo(chapterNo);
		Example<SutraEntity> example = Example.of(entity);
		return sutraMapper.map(sutraRepo.findOne(example));
	}

	public List<Sutra> getSutras(Long bookId, int chapterNo, String script, int startIndex, int size) {
		SutraEntity entity = new SutraEntity();
		entity.setBook(bookRepo.findOne(bookId));
		entity.setLangCode(script);
		entity.setChapterNo(chapterNo);
		Example<SutraEntity> example = Example.of(entity);
		final PageRequest page = new PageRequest(
				startIndex, size, Sort.Direction.ASC, "verseNo"
		);
		return sutraMapper.map(sutraRepo.findAll(example, page).getContent());
	}

	public List<Book> getBooks() {
		return bookMapper.map(bookRepo.findAll());
	}

	public Book getBookById(Long id) {
		return bookMapper.map(bookRepo.findOne(id));
	}
}
