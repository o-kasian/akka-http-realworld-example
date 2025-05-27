package com.realworld.articles;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RestController
@RequestMapping("/articles")
public class ArticleRoute {

    private final CommentRoute commentRoute;
    private final ArticleService articleService;

    public ArticleRoute(CommentRoute commentRoute, ArticleService articleService) {
        this.commentRoute = commentRoute;
        this.articleService = articleService;
    }

    @GetMapping
    public CompletionStage<ResponseEntity<?>> getArticles(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String favorited,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) Long offset) {
        ArticleRequest request = new ArticleRequest(tag, authorName, favorited, limit, offset);
        return articleService.getArticles(request)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletionStage<ResponseEntity<?>> createArticle(
            @RequestAttribute String userId,
            @RequestBody CreateArticle article) {
        return articleService.createArticle(userId, article.getArticle(), Optional.of(userId))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/feed")
    public CompletionStage<ResponseEntity<?>> getFeed(
            @RequestAttribute String userId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {
        return articleService.getFeeds(userId, Optional.ofNullable(limit), Optional.ofNullable(offset))
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{slug}")
    public CompletionStage<ResponseEntity<?>> getArticle(
            @PathVariable String slug,
            @RequestAttribute String userId) {
        return articleService.getArticleBySlug(slug, userId)
                .thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{slug}")
    public CompletionStage<ResponseEntity<?>> updateArticle(
            @PathVariable String slug,
            @RequestAttribute String userId,
            @RequestBody UpdateArticle updateArticle) {
        return articleService.updateArticleBySlug(slug, userId, updateArticle.getArticle())
                .thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{slug}")
    public CompletionStage<ResponseEntity<?>> deleteArticle(
            @PathVariable String slug) {
        return articleService.deleteArticleBySlug(slug)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/{slug}/favorite")
    public CompletionStage<ResponseEntity<?>> favoriteArticle(
            @PathVariable String slug,
            @RequestAttribute String userId) {
        return articleService.favoriteArticle(userId, slug)
                .thenApply(result -> result
                        .map(article -> ResponseEntity.ok(article))
                        .orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{slug}/favorite")
    public CompletionStage<ResponseEntity<?>> unfavoriteArticle(
            @PathVariable String slug,
            @RequestAttribute String userId) {
        return articleService.unFavoriteArticle(userId, slug)
                .thenApply(result -> result
                        .map(article -> ResponseEntity.ok(article))
                        .orElse(ResponseEntity.notFound().build()));
    }
}

class UpdateArticle {
    private ArticleUpdated article;

    public ArticleUpdated getArticle() {
        return article;
    }

    public void setArticle(ArticleUpdated article) {
        this.article = article;
    }
}

class CreateArticle {
    private ArticlePosted article;

    public ArticlePosted getArticle() {
        return article;
    }

    public void setArticle(ArticlePosted article) {
        this.article = article;
    }
}

class FeedRequest {
    private Optional<Long> limit = Optional.of(100L);
    private Optional<Long> offset = Optional.of(0L);

    public Optional<Long> getLimit() {
        return limit;
    }

    public void setLimit(Optional<Long> limit) {
        this.limit = limit;
    }

    public Optional<Long> getOffset() {
        return offset;
    }

    public void setOffset(Optional<Long> offset) {
        this.offset = offset;
    }
}