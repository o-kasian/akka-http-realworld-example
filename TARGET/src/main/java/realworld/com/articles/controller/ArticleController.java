package realworld.com.articles.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import realworld.com.articles.dto.ArticleRequest;
import realworld.com.articles.dto.ArticleResponse;
import realworld.com.articles.dto.ArticlesResponse;
import realworld.com.articles.dto.CreateArticleRequest;
import realworld.com.articles.dto.UpdateArticleRequest;
import realworld.com.articles.service.ArticleService;
import realworld.com.security.UserPrincipal;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    
    @GetMapping
    public ResponseEntity<ArticlesResponse> getArticles(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String favorited,
            @RequestParam(required = false) Long limit,
            @RequestParam(required = false) Long offset) {
        
        ArticleRequest request = ArticleRequest.builder()
            .tag(tag)
            .authorName(authorName)
            .favorited(favorited)
            .limit(limit)
            .offset(offset)
            .build();
            
        return ResponseEntity.ok(articleService.getArticles(request));
    }
    
    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CreateArticleRequest request) {
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(articleService.createArticle(userPrincipal.getId(), request.getArticle()));
    }
    
    @GetMapping("/feed")
    public ResponseEntity<ArticlesResponse> getFeed(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {
        
        return ResponseEntity.ok(
            articleService.getFeeds(userPrincipal.getId(), limit, offset));
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String slug) {
        
        return articleService.getArticleBySlug(slug, userPrincipal.getId())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String slug,
            @RequestBody UpdateArticleRequest request) {
        
        return articleService.updateArticleBySlug(slug, userPrincipal.getId(), request.getArticle())
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable String slug) {
        
        articleService.deleteArticleBySlug(slug);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{slug}/favorite")
    public ResponseEntity<ArticleResponse> favoriteArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String slug) {
        
        return articleService.favoriteArticle(userPrincipal.getId(), slug)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<ArticleResponse> unfavoriteArticle(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable String slug) {
        
        return articleService.unfavoriteArticle(userPrincipal.getId(), slug)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}