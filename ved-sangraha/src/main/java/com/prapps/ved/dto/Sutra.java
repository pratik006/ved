package com.prapps.ved.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sutra implements DatastoreObject {
	public static final String KIND = "SUTRA";
	private Long id;
	private Long chapterNo;
	private String chapterName;
	private Long sutraNo;
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

	public Long getChapterNo() {
		return chapterNo;
	}

	public void setChapterNo(Long chapterNo) {
		this.chapterNo = chapterNo;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public Long getSutraNo() {
		return sutraNo;
	}

	public void setSutraNo(Long sutraNo) {
		this.sutraNo = sutraNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Commentary> getCommentaries() {
		if (commentaries == null) {
			commentaries = new ArrayList<>();
		}

		return commentaries;
	}

	public void setCommentaries(List<Commentary> commentaries) {
		this.commentaries = commentaries;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Sutra) {
			Sutra otherSutra = (Sutra) other;
			return chapterNo == otherSutra.getChapterNo() && sutraNo == otherSutra.getSutraNo() && content.equals(otherSutra.getContent())
					&& commentaries.equals(otherSutra.getCommentaries());
		}

		return false;
	}

	@Override
	public List<String> properties() {
		return Arrays.asList(CHAPTER_NO, SUTRA_NO, CONTENT, COMMENTARIES);
	}

	@Override
	public String getKind() {
		return KIND;
	}
}
