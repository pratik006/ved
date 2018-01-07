package com.prapps.ved.mapper;

import com.prapps.ved.dto.Language;
import com.prapps.ved.persistence.LanguageEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class LanguageMapper {
    public Language map(LanguageEntity entity) {
        Language language = new Language();
        BeanUtils.copyProperties(entity, language);
        return language;
    }

    public List<Language> map(Collection<LanguageEntity> entities) {
        List<Language> languages = new ArrayList<>();
        for (LanguageEntity entity : entities) {
            languages.add(map(entity));
        }
        return languages;
    }
}
