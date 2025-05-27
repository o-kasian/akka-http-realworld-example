package com.realworld.tags;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/tags")
public class TagRoute {

    private final TagService tagService;

    public TagRoute(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public CompletableFuture<ResponseEntity<?>> getTags() {
        return tagService.getTags()
                .thenApply(ResponseEntity::ok);
    }
}