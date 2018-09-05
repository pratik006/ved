package com.prapps.ved.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Commentary implements DatastoreObject {
	private Long id;
	private Long chapterNo;
	private Long sutraNo;
	private String commentator;
	private String language;
	private String content;

	public static final String KIND = "commentary";
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

	public Long getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(Long chapterNo) {
		this.chapterNo = chapterNo;
	}

	public Long getSutraNo() {
		return sutraNo;
	}

	public void setSutraNo(Long sutraNo) {
		this.sutraNo = sutraNo;
	}
	
	@Override
	public List<String> properties() {
		return Arrays.asList(CHAPTER_NO, SUTRA_NO, LANGUAGE, COMMENTATOR, CONTENT);
	}

	@Override
	public String getKind() {
		return KIND;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Commentary) {
			Commentary otherObj = (Commentary) obj;
			return this.getChapterNo() == otherObj.getChapterNo()
					&& this.getSutraNo() == otherObj.getSutraNo()
					&& this.getContent().equals(otherObj.getContent());
		}

		return false;
	}
}
