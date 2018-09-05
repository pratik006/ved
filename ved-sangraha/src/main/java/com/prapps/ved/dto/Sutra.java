package com.prapps.ved.dto;

import java.util.Arrays;
import java.util.List;

public class Sutra implements DatastoreObject {
	private Long id;
	private Integer chapterNo;
	private String chapterName;
	private Integer sutraNo;
	private String content;

	public static final String CHAPTER_NO = "chapterNo";
	public static final String SUTRA_NO = "sutraNo";
	public static final String CONTENT = "content";
	public static final String COMMENTARIES = "commentaries";
	
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

	public Integer getSutraNo() {
		return sutraNo;
	}

	public void setSutraNo(Integer sutraNo) {
		this.sutraNo = sutraNo;
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

	@Override
	public List<String> properties() {
		return Arrays.asList(CHAPTER_NO, SUTRA_NO, CONTENT, COMMENTARIES);
	}
}
