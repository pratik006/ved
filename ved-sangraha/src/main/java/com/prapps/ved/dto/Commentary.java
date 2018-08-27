package com.prapps.ved.dto;

import java.util.Arrays;
import java.util.List;

public class Commentary implements DatastoreObject {
	private Long id;
	private int chapterNo;
	private int sutraNo;
	private String commentator;
	private String language;
	private String content;

	public static final String CHAPTER_NO = "chapterNo";
	public static final String SUTRA_NO = "sutraNo";
	public static final String COMMENTATOR = "commentator";
	public static final String LANGUAGE = "language";
	public static final String CONTENT = "content";

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCommentator() {
		return commentator;
	}
	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public int getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(int chapterNo) {
		this.chapterNo = chapterNo;
	}

	public int getSutraNo() {
		return sutraNo;
	}

	public void setSutraNo(int sutraNo) {
		this.sutraNo = sutraNo;
	}
	
	@Override
	public List<String> properties() {
		return Arrays.asList(CHAPTER_NO, SUTRA_NO, LANGUAGE, COMMENTATOR, CONTENT);
	}
}
