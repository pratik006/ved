package com.prapps.ved.persistence;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String name;
	private String authorName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
	private Set<SutraEntity> sutras;

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

	public Set<SutraEntity> getVerses() {
		if (null == sutras) {
			sutras = new HashSet<>();
		}
		return sutras;
	}

	public void setVerses(Set<SutraEntity> verses) {
		this.sutras = verses;
	}
}
