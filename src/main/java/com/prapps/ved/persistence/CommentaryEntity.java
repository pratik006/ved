package com.prapps.ved.persistence;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "commentary")
public class CommentaryEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String commentator;
	private String language;
	@Column(length = 10000)
	private String content;
	@ManyToOne
	@JoinColumn(name = "sutra_id")
	private SutraEntity sutra;
	
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
	
	public SutraEntity getSutra() {
		return sutra;
	}
	public void setSutra(SutraEntity sutra) {
		this.sutra = sutra;
	}
	public int hashCode() {
		return Objects.hash(commentator, language);
	}
	
	@Override
	public boolean equals(Object otherObj) {
		CommentaryEntity other = (CommentaryEntity) otherObj;
		return this.getCommentator().equals(other.getCommentator()) && this.getLanguage().equals(other.getLanguage());
	}
}
