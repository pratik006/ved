package com.prapps.ved.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.prapps.ved.dto.Sutra;
import com.prapps.ved.mapper.SutraMapper;
import com.prapps.ved.persistence.SutraEntity;
import com.prapps.ved.persistence.repo.BookRepo;
import com.prapps.ved.persistence.repo.SutraRepo;

@Service
public class VedService {
	@Autowired private SutraRepo sutraRepo;
	@Autowired private BookRepo bookRepo;
	@Autowired SutraMapper sutraMapper;

	public Sutra getSutra(Long bookId, Integer chapterNo, Integer sutraNo, String script) {
		SutraEntity entity = new SutraEntity();
		entity.setBook(bookRepo.findOne(bookId));
		entity.setVerseNo(sutraNo);
		entity.setLangCode(script);
		entity.setChapterNo(chapterNo);
		Example<SutraEntity> example = Example.of(entity);
		return sutraMapper.map(sutraRepo.findOne(example));
	}
}
