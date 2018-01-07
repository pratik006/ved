package com.prapps.ved.persistence.repo;

import com.prapps.ved.persistence.ChapterEntity;
import com.prapps.ved.persistence.ChapterIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepo extends JpaRepository<ChapterEntity, ChapterIdEntity> {
}
