package com.prapps.ved.persistence.repo;

import com.prapps.ved.persistence.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prapps.ved.persistence.SutraEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SutraRepo extends JpaRepository<SutraEntity, Long> {
    @Query("select s from SutraEntity s " +
            " where s.sutraNo >= :start and s.sutraNo <= :end and s.chapterNo = :chapterNo and s.language.code = :langCode" +
            " and s.bookId = :bookId")
    List<SutraEntity> findBySutraNoBetween(@Param("bookId") Long bookId,
                                           @Param("chapterNo") int chapterNo,
                                           @Param("langCode") String langCode,
                                           @Param("start") int start,
                                           @Param("end") int end);

    @Query("select distinct s.language from SutraEntity s where s.chapter.id.bookId= :bookId")
    List<LanguageEntity> getAvailableScripts(@Param("bookId") Long bookId);
}
