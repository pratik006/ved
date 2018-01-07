package com.prapps.ved.mapper;

import com.prapps.ved.dto.Commentary;
import com.prapps.ved.persistence.CommentaryEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CommentaryMapper {
    public Commentary map(CommentaryEntity entity) {
        Commentary commentary = new Commentary();
        BeanUtils.copyProperties(entity, commentary);
        return commentary;
    }

    public List<Commentary> map(Collection<CommentaryEntity> entities) {
        List<Commentary> commentaries = new ArrayList<>();
        for (CommentaryEntity entity : entities) {
            commentaries.add(map(entity));
        }
        return commentaries;
    }
}
