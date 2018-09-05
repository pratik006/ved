package com.prapps.ved.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Book {
	private Long id;
	private String code;
	private String name;
	private String authorName;
	private String previewUrl;
	private List<Language> availableLanguages;
	private List<Commentary> availableCommentaries;

	private Set<Sutra> sutras;

	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String AUTHOR_NAME = "authorName";
	public static final String PREVIEW_URL = "previewUrl";
	public static final String LANGUAGES = "availableLanguages";
	public static final String COMMENTARIES = "availableCommentaries";

	public Book() { }

	public Book(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private List<Chapter> chapters;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPreviewUrl() { return previewUrl; }
	public void setPreviewUrl(String previewUrl) { this.previewUrl = previewUrl; }

	public List<Chapter> getChapters() { if (chapters == null) { chapters = new ArrayList<>(); } return chapters; }
	public void setChapters(List<Chapter> chapters) { this.chapters = chapters; }

	public List<Language> getAvailableLanguages() { return availableLanguages; }
	public void setAvailableLanguages(List<Language> availableLanguages) { this.availableLanguages = availableLanguages; }

	public List<Commentary> getAvailableCommentaries() { return availableCommentaries; }
	public void setAvailableCommentaries(List<Commentary> commentaries) { this.availableCommentaries = commentaries; }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<Sutra> getSutras() {
		if (sutras == null) {
			sutras = new HashSet<>();
		}

		return sutras;
	}

	public void setSutras(Set<Sutra> sutras) {
		this.sutras = sutras;
	}
}
