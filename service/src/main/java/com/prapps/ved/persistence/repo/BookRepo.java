package com.prapps.ved.persistence.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prapps.ved.persistence.BookEntity;

@Repository
public interface BookRepo extends JpaRepository<BookEntity, Long> {
	BookEntity findByNameInIgnoreCase(String name);
	@EntityGraph(value = "BookEntity.detail", type = EntityGraph.EntityGraphType.LOAD)
	BookEntity findOne(Long id);
}
