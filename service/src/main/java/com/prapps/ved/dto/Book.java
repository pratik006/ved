package com.prapps.ved.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Book {
	private Long id;
	private String name;
	private String authorName;
	private String previewUrl;

	public String getName() {
		return name;
	}
	public Book(String name) {
		this.name = name;
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
}
