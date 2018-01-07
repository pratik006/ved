package com.prapps.ved.persistence.repo;

import com.prapps.ved.persistence.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<LanguageEntity, String> {
}
