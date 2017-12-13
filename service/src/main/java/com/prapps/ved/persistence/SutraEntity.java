package com.prapps.ved.persistence;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sutra")
public class SutraEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer sutraNo;
	@Column(length = 10000, nullable = false)
	private String content;
	@Column(nullable = false)
	private String langCode;
	@Column(nullable = false)
	private String language;

	@ManyToOne
	@JoinColumn(name = "chapter_id")
	private ChapterEntity chapter;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sutra")
	private Set<CommentaryEntity> commentaries;

	public Long getId() {
		return id;
	}
	public void setId(Long id) { this.id = id; }

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

	public String getLangCode() {
		return langCode;
	}
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public Set<CommentaryEntity> getCommentaries() {
		if (null == commentaries) {
			commentaries = new HashSet<>();
		}
		return commentaries;
	}

	public void setCommentaries(Set<CommentaryEntity> commentaries) {
		this.commentaries = commentaries;
	}

	public ChapterEntity getChapter() { return chapter; }
	public void setChapter(ChapterEntity chapter) { this.chapter = chapter; }

	public int hashCode() {
		return Objects.hash( id, sutraNo, langCode );
	}

	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
}
