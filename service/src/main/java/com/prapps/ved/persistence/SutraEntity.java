package com.prapps.ved.persistence;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "sutra")
public class SutraEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private Integer sutraNo;
	@Column(insertable = false, updatable = false)
	private Integer chapterNo;
	@Column(insertable = false, updatable = false)
	private Long bookId;
	@Column(length = 10000, nullable = false)
	private String content;

	@ManyToOne
	@JoinColumn(name = "lang_code", insertable = false, updatable = false)
	private LanguageEntity language;

	@ManyToOne
	@JoinColumns({
		@JoinColumn(name = "bookId"),
		@JoinColumn(name = "chapterNo")
	})
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

	public Long getBookId() { return bookId; }
	public void setBookId(Long bookId) { this.bookId = bookId; }

	public Integer getChapterNo() { return chapterNo; }
	public void setChapterNo(Integer chapterNo) { this.chapterNo = chapterNo; }

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return Objects.hash( id, sutraNo );
	}

	public LanguageEntity getLanguage() {
		return language;
	}
	public void setLanguage(LanguageEntity language) {
		this.language = language;
	}
}
