package realworld.com.articles.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import realworld.com.articles.dto.ArticleDto;
import realworld.com.articles.dto.ArticleRequest;
import realworld.com.articles.dto.ArticleResponse;
import realworld.com.articles.dto.ArticlesResponse;
import realworld.com.articles.dto.CreateArticleRequest.ArticleCreateDto;
import realworld.com.articles.dto.UpdateArticleRequest.ArticleUpdateDto;
import realworld.com.articles.model.Article;
import realworld.com.articles.model.ArticleTag;
import realworld.com.articles.model.Favorite;
import realworld.com.articles.model.Tag;
import realworld.com.articles.repository.ArticleRepository;
import realworld.com.articles.repository.ArticleTagRepository;
import realworld.com.articles.repository.FavoriteRepository;
import realworld.com.articles.repository.TagRepository;
import realworld.com.profile.dto.ProfileDto;
import realworld.com.profile.service.ProfileService;
import realworld.com.users.model.User;
import realworld.com.users.repository.UserRepository;
import realworld.com.utils.DateUtils;
import realworld.com.utils.SlugUtils;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProfileService profileService;

    public ArticlesResponse getArticles(ArticleRequest request) {
        // Implementation would include filtering by tag, author, favorited
        List<Article> articles = articleRepository.findAll();
        
        List<Long> articleIds = articles.stream()
            .map(Article::getId)
            .collect(Collectors.toList());
            
        List<Long> authorIds = articles.stream()
            .map(Article::getAuthorId)
            .collect(Collectors.toList());
            
        Map<Long, User> authors = userRepository.findAllById(authorIds).stream()
            .collect(Collectors.toMap(User::getId, user -> user));
            
        Map<Long, List<String>> tagsByArticle = getTagsByArticleIds(articleIds);
        
        List<ArticleDto> articleDtos = articles.stream()
            .map(article -> convertToDto(article, authors.get(article.getAuthorId()), 
                tagsByArticle.getOrDefault(article.getId(), List.of()), false, 0))
            .collect(Collectors.toList());
            
        return new ArticlesResponse(articleDtos, articleDtos.size());
    }
    
    @Transactional
    public ArticleResponse createArticle(Long authorId, ArticleCreateDto articleDto) {
        Article article = new Article();
        article.setSlug(SlugUtils.slugify(articleDto.getTitle()));
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setBody(articleDto.getBody());
        article.setAuthorId(authorId);
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        
        Article savedArticle = articleRepository.save(article);
        
        List<Tag> tags = createTags(articleDto.getTagList());
        connectTagsToArticle(tags, savedArticle.getId());
        
        User author = userRepository.findById(authorId).orElseThrow();
        ProfileDto profile = profileService.getProfile(author.getUsername(), authorId);
        
        ArticleDto articleResponse = convertToDto(savedArticle, author, 
            tags.stream().map(Tag::getName).collect(Collectors.toList()), 
            false, 0);
            
        return new ArticleResponse(articleResponse);
    }
    
    public ArticlesResponse getFeeds(Long userId, Integer limit, Integer offset) {
        List<Article> articles = articleRepository.findArticlesByFollowees(userId);
        
        List<Long> articleIds = articles.stream()
            .map(Article::getId)
            .collect(Collectors.toList());
            
        List<Long> authorIds = articles.stream()
            .map(Article::getAuthorId)
            .collect(Collectors.toList());
            
        Map<Long, User> authors = userRepository.findAllById(authorIds).stream()
            .collect(Collectors.toMap(User::getId, user -> user));
            
        List<Long> favoriteArticleIds = favoriteRepository.findFavoritedArticleIds(userId, articleIds);
        
        Map<Long, Integer> favoritesCounts = new HashMap<>();
        favoriteRepository.countFavoritesByArticleIds(articleIds).forEach(
            result -> favoritesCounts.put((Long) result[0], ((Number) result[1]).intValue())
        );
        
        Map<Long, List<String>> tagsByArticle = getTagsByArticleIds(articleIds);
        
        List<ArticleDto> articleDtos = articles.stream()
            .map(article -> convertToDto(article, authors.get(article.getAuthorId()), 
                tagsByArticle.getOrDefault(article.getId(), List.of()), 
                favoriteArticleIds.contains(article.getId()),
                favoritesCounts.getOrDefault(article.getId(), 0)))
            .collect(Collectors.toList());
            
        return new ArticlesResponse(articleDtos, articleDtos.size());
    }
    
    public Optional<ArticleResponse> getArticleBySlug(String slug, Long userId) {
        return articleRepository.findBySlug(slug)
            .map(article -> {
                User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                
                List<String> tagNames = tagRepository.findTagsByArticleId(article.getId())
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
                    
                boolean favorited = favoriteRepository
                    .findByUserIdAndFavoritedId(userId, article.getId())
                    .isPresent();
                    
                int favoritesCount = favoriteRepository.countByFavoritedId(article.getId());
                
                ProfileDto profile = profileService.getProfile(author.getUsername(), userId);
                
                ArticleDto articleDto = convertToDto(article, author, tagNames, favorited, favoritesCount);
                
                return new ArticleResponse(articleDto);
            });
    }
    
    @Transactional
    public Optional<ArticleResponse> updateArticleBySlug(String slug, Long userId, ArticleUpdateDto articleUpdate) {
        return articleRepository.findBySlug(slug)
            .filter(article -> article.getAuthorId().equals(userId))
            .map(article -> {
                if (articleUpdate.getTitle() != null) {
                    article.setTitle(articleUpdate.getTitle());
                    article.setSlug(SlugUtils.slugify(articleUpdate.getTitle()));
                }
                
                if (articleUpdate.getDescription() != null) {
                    article.setDescription(articleUpdate.getDescription());
                }
                
                if (articleUpdate.getBody() != null) {
                    article.setBody(articleUpdate.getBody());
                }
                
                article.setUpdatedAt(LocalDateTime.now());
                Article updatedArticle = articleRepository.save(article);
                
                User author = userRepository.findById(updatedArticle.getAuthorId()).orElseThrow();
                
                List<String> tagNames = tagRepository.findTagsByArticleId(updatedArticle.getId())
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
                    
                boolean favorited = favoriteRepository
                    .findByUserIdAndFavoritedId(userId, updatedArticle.getId())
                    .isPresent();
                    
                int favoritesCount = favoriteRepository.countByFavoritedId(updatedArticle.getId());
                
                ProfileDto profile = profileService.getProfile(author.getUsername(), userId);
                
                ArticleDto articleDto = convertToDto(updatedArticle, author, tagNames, favorited, favoritesCount);
                
                return new ArticleResponse(articleDto);
            });
    }
    
    @Transactional
    public void deleteArticleBySlug(String slug) {
        articleRepository.deleteBySlug(slug);
    }
    
    @Transactional
    public Optional<ArticleResponse> favoriteArticle(Long userId, String slug) {
        return articleRepository.findBySlug(slug)
            .map(article -> {
                favoriteRepository.findByUserIdAndFavoritedId(userId, article.getId())
                    .orElseGet(() -> {
                        Favorite favorite = new Favorite();
                        favorite.setUserId(userId);
                        favorite.setFavoritedId(article.getId());
                        return favoriteRepository.save(favorite);
                    });
                
                User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                
                List<String> tagNames = tagRepository.findTagsByArticleId(article.getId())
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
                    
                int favoritesCount = favoriteRepository.countByFavoritedId(article.getId());
                
                ProfileDto profile = profileService.getProfile(author.getUsername(), userId);
                
                ArticleDto articleDto = convertToDto(article, author, tagNames, true, favoritesCount);
                
                return new ArticleResponse(articleDto);
            });
    }
    
    @Transactional
    public Optional<ArticleResponse> unfavoriteArticle(Long userId, String slug) {
        return articleRepository.findBySlug(slug)
            .map(article -> {
                favoriteRepository.deleteByUserIdAndFavoritedId(userId, article.getId());
                
                User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                
                List<String> tagNames = tagRepository.findTagsByArticleId(article.getId())
                    .stream()
                    .map(Tag::getName)
                    .collect(Collectors.toList());
                    
                int favoritesCount = favoriteRepository.countByFavoritedId(article.getId());
                
                ProfileDto profile = profileService.getProfile(author.getUsername(), userId);
                
                ArticleDto articleDto = convertToDto(article, author, tagNames, false, favoritesCount);
                
                return new ArticleResponse(articleDto);
            });
    }
    
    private List<Tag> createTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return List.of();
        }
        
        List<Tag> existingTags = tagRepository.findByNameIn(tagNames);
        List<String> existingTagNames = existingTags.stream()
            .map(Tag::getName)
            .collect(Collectors.toList());
            
        List<Tag> newTags = tagNames.stream()
            .filter(name -> !existingTagNames.contains(name))
            .map(Tag::create)
            .collect(Collectors.toList());
            
        if (!newTags.isEmpty()) {
            newTags = tagRepository.saveAll(newTags);
        }
        
        List<Tag> allTags = new ArrayList<>(existingTags);
        allTags.addAll(newTags);
        return allTags;
    }
    
    private void connectTagsToArticle(List<Tag> tags, Long articleId) {
        List<ArticleTag> articleTags = tags.stream()
            .map(tag -> {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                articleTag.setTagId(tag.getId());
                return articleTag;
            })
            .collect(Collectors.toList());
            
        articleTagRepository.saveAll(articleTags);
    }
    
    private Map<Long, List<String>> getTagsByArticleIds(List<Long> articleIds) {
        Map<Long, List<String>> result = new HashMap<>();
        
        tagRepository.findTagsByArticleIds(articleIds).forEach(row -> {
            Long articleId = (Long) row[0];
            Tag tag = (Tag) row[1];
            
            result.computeIfAbsent(articleId, k -> new ArrayList<>())
                .add(tag.getName());
        });
        
        return result;
    }
    
    private ArticleDto convertToDto(Article article, User author, List<String> tagList, 
                                   boolean favorited, int favoritesCount) {
        ProfileDto profile = ProfileDto.builder()
            .username(author.getUsername())
            .bio(author.getBio())
            .image(author.getImage())
            .following(false) // This would need to be determined based on current user
            .build();
            
        return ArticleDto.builder()
            .slug(article.getSlug())
            .title(article.getTitle())
            .description(article.getDescription())
            .body(article.getBody())
            .tagList(tagList)
            .createdAt(DateUtils.formatISO8601(article.getCreatedAt()))
            .updatedAt(DateUtils.formatISO8601(article.getUpdatedAt()))
            .favorited(favorited)
            .favoritesCount(favoritesCount)
            .author(profile)
            .build();
    }
}