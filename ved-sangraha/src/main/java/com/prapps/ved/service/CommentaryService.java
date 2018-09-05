package com.prapps.ved.service;

import com.google.appengine.api.datastore.*;
import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.CommentarySearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaryService {
    @Autowired
    private DatastoreService datastore;

    public List<String> save(List<Commentary> commentaries) {
        return commentaries.stream().map(commentary -> {
            Entity entity = new Entity(Commentary.KIND);
            entity.setProperty(Commentary.LANGUAGE, commentary.getLanguage());
            entity.setProperty(Commentary.CONTENT, commentary.getContent());
            entity.setProperty(Commentary.COMMENTATOR, commentary.getCommentator());
            entity.setProperty(Commentary.SUTRA_NO, commentary.getSutraNo());
            entity.setProperty(Commentary.CHAPTER_NO, commentary.getChapterNo());
            Key key = datastore.put(entity);
            return key.toString();
        }).collect(Collectors.toList());
    }

    public List<Commentary> findCommentary(CommentarySearchRequest req) {
        Query.Filter propertyFilter = new Query.FilterPredicate(
                Commentary.LANGUAGE, Query.FilterOperator.EQUAL, req.getLanguage());
        Query q = new Query(Commentary.KIND).setFilter(propertyFilter);
        List<Entity> result = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        return result.stream().map(comm -> {
            Commentary commentary = new Commentary();
            commentary.setLanguage(String.valueOf(comm.getProperty(Commentary.LANGUAGE)));
            commentary.setCommentator(String.valueOf(comm.getProperty(Commentary.COMMENTATOR)));
            commentary.setContent(String.valueOf(comm.getProperty(Commentary.CONTENT)));
            commentary.setChapterNo( (Long)comm.getProperty(Commentary.CHAPTER_NO) );
            commentary.setSutraNo( (Long)comm.getProperty(Commentary.SUTRA_NO) );
            return commentary;
        }).collect(Collectors.toList());
    }
}
