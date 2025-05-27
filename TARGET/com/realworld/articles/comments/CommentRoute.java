package com.realworld.articles.comments;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class CommentRoute {

    private final CommentService commentService;

    public CommentRoute(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{slug}/comments")
    public CompletableFuture<CommentsResponse> getComments(
            @PathVariable String slug,
            @AuthenticationPrincipal Long userId) {
        return commentService.getComments(slug, userId).toCompletableFuture();
    }

    @PostMapping("/{slug}/comments")
    public CompletableFuture<ResponseEntity<?>> createComment(
            @PathVariable String slug,
            @AuthenticationPrincipal Long userId,
            @RequestBody CommentRequest comment) {
        return commentService.createComment(slug, userId, comment)
                .thenApply(optionalResponse ->
                        optionalResponse
                                .map(response -> ResponseEntity.ok(response))
                                .orElse(ResponseEntity.notFound().build()))
                .toCompletableFuture();
    }

    @DeleteMapping("/{slug}/comments/{commentId}")
    public CompletableFuture<ResponseEntity<Void>> deleteComment(
            @PathVariable String slug,
            @PathVariable Long commentId) {
        return commentService.deleteComment(slug, commentId)
                .thenApply(count -> ResponseEntity.status(HttpStatus.OK).build())
                .toCompletableFuture();
    }
}