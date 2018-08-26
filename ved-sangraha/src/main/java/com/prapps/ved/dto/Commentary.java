package com.prapps.ved.dto;

public class Commentary {
	private Long id;
	private int chapterNo;
	private int sutraNo;
	private String commentator;
	private String language;
	private String content;
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
}