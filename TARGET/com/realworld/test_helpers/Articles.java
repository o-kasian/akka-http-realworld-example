package com.realworld.test_helpers;

import com.realworld.articles.Article;

public class Articles {
    public static final Article NORMAL_ARTICLE = Article.builder()
            .id(1L)
            .slug("slug")
            .title("title")
            .description("description")
            .body("body")
            .authorId(1L)
            .createdAt(Dates.currentWhenInserting())
            .updatedAt(Dates.currentWhenInserting())
            .build();
}