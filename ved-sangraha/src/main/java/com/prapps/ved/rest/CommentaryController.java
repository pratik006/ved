package com.prapps.ved.rest;

import com.prapps.ved.dto.Commentary;
import com.prapps.ved.dto.CommentarySearchRequest;
import com.prapps.ved.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/commentary")
public class CommentaryController {
    @Autowired
    CommentaryService service;

    @RequestMapping(method = RequestMethod.POST)
    public List<String> save(List<Commentary> commentaries) {
        return service.save(commentaries);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Commentary> findCommentary(@RequestBody CommentarySearchRequest req) {
        return service.findCommentary(req);
    }
}
