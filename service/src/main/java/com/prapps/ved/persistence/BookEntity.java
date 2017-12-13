package com.prapps.ved.persistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "book")
@NamedEntityGraph(name = "BookEntity.detail", attributeNodes = @NamedAttributeNode("chapters"))
public class BookEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;
	private String authorName;
	@Column(length = 1000)
	private String previewUrl;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	@OrderBy("id asc")
	private List<ChapterEntity> chapters;

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

	public List<ChapterEntity> getChapters() {
		if (chapters == null) {
			chapters = new ArrayList<>();
		}

		return chapters;
	}

	public void setChapters(List<ChapterEntity> chapters) {
		this.chapters = chapters;
	}
}
