package com.prapps.ved.mapper;

import com.prapps.ved.dto.Chapter;
import com.prapps.ved.persistence.ChapterEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChapterMapper {
    @Autowired SutraMapper sutraMapper;

    public Chapter map(ChapterEntity entity, boolean detail) {
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(entity, chapter);
        chapter.setSutras(detail?sutraMapper.map(entity.getSutras()):Collections.emptyList());
        chapter.setChapterNo(entity.getId().getChapterNo());
        return chapter;
    }

    public Chapter map(ChapterEntity entity) {
        return map(entity, Boolean.FALSE);
    }

    public List<Chapter> map(Collection<ChapterEntity> entities) {
        List<Chapter> chapters = new ArrayList<>(entities.size());
        entities.forEach(entity -> chapters.add(map(entity)));
        return chapters;
    }
}
