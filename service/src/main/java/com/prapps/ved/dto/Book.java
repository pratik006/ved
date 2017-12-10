package com.prapps.ved.dto;

import java.util.List;

public class Book {
	private Long id;
	private String name;
	private String authorName;
	private String previewUrl;
	
	public Book(String name) {
		this.name = name;
	}
	
	private List<Sutra> verses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getPreviewUrl() { return previewUrl; }

	public void setPreviewUrl(String previewUrl) { this.previewUrl = previewUrl; }

	public List<Sutra> getVerses() {
		return verses;
	}

	public void setVerses(List<Sutra> verses) {
		this.verses = verses;
	}
}
