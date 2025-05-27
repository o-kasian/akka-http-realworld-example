package com.realworld.tags;

import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public CompletableFuture<ResponseTags> getTags() {
        return CompletableFuture.supplyAsync(() -> 
            new ResponseTags(tagRepository.findAll())
        );
    }
}