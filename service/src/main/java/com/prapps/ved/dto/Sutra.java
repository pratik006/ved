package com.prapps.ved.dto;

import java.util.List;

public class Sutra {
	private Long id;
	private Integer chapterNo;
	private String chapterName;
	private Integer verseNo;
	private String content;
	
	private List<Commentary> commentaries;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(Integer chapterNo) {
		this.chapterNo = chapterNo;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Integer getVerseNo() {
		return verseNo;
	}

	public void setVerseNo(Integer verseNo) {
		this.verseNo = verseNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Commentary> getCommentaries() {
		return commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}
}
