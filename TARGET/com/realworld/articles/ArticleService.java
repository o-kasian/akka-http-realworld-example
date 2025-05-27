package com.realworld.articles;

import com.realworld.core.User;
import com.realworld.profile.Profile;
import com.realworld.tags.TagRepository;
import com.realworld.tags.TagV;
import com.realworld.users.UserRepository;
import com.realworld.utils.ISO8601;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    public ArticleService(ArticleRepository articleRepository,
                         UserRepository userRepository,
                         TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    public ForResponseArticles getArticles(ArticleRequest request) {
        List<Article> articles = articleRepository.getArticles(request);
        Map<Long, User> authors = userRepository.findAllById(articles.stream()
                .map(Article::getAuthorId)
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(User::getId, author -> author));
        
        Map<Long, List<TagV>> tags = tagRepository.getTagsByArticleIds(
                articles.stream().map(Article::getId).collect(Collectors.toList()));

        List<ArticleForResponse> articleResponses = articles.stream()
                .map(article -> new ArticleForResponse(
                        article.getSlug(),
                        article.getTitle(),
                        article.getDescription(),
                        article.getBody(),
                        tags.getOrDefault(article.getId(), List.of()).stream()
                                .map(TagV::getName)
                                .collect(Collectors.toList()),
                        ISO8601.format(article.getCreatedAt()),
                        ISO8601.format(article.getUpdatedAt()),
                        false,
                        0,
                        convertUserToProfile(Optional.ofNullable(authors.get(article.getAuthorId())))
                )).collect(Collectors.toList());

        return new ForResponseArticles(articleResponses, articles.size());
    }

    @Transactional
    public Optional<ForResponseArticle> createArticle(Long authorId, ArticlePosted newArticle, Optional<Long> currentUserId) {
        Article article = articleRepository.save(newArticle.create(authorId));
        List<TagV> tags = createTags(newArticle.getTagList());
        connectTagArticle(tags, article.getId());
        return getArticleResponse(article, tags, currentUserId);
    }

    public ForResponseArticles getFeeds(Long userId, Optional<Integer> limit, Optional<Integer> offset) {
        List<Article> articles = articleRepository.getArticlesByFollowees(userId, limit, offset);
        Set<Long> favorites = articleRepository.getFavoriteArticleIds(userId, articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList()));
        
        Map<Long, Integer> favoriteCount = articleRepository.countFavorites(articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList()));

        Map<Long, User> authors = userRepository.findAllById(articles.stream()
                .map(Article::getAuthorId)
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(User::getId, author -> author));

        Map<Long, List<TagV>> tags = tagRepository.getTagsByArticleIds(
                articles.stream().map(Article::getId).collect(Collectors.toList()));

        List<ArticleForResponse> articleResponses = articles.stream()
                .map(article -> new ArticleForResponse(
                        article.getSlug(),
                        article.getTitle(),
                        article.getDescription(),
                        article.getBody(),
                        tags.getOrDefault(article.getId(), List.of()).stream()
                                .map(TagV::getName)
                                .collect(Collectors.toList()),
                        ISO8601.format(article.getCreatedAt()),
                        ISO8601.format(article.getUpdatedAt()),
                        favorites.contains(article.getId()),
                        favoriteCount.getOrDefault(article.getId(), 0),
                        convertUserToProfile(Optional.ofNullable(authors.get(article.getAuthorId())))
                )).collect(Collectors.toList());

        return new ForResponseArticles(articleResponses, articles.size());
    }

    public Optional<ForResponseArticle> getArticleBySlug(String slug, Long userId) {
        return articleRepository.findBySlug(slug)
                .map(article -> {
                    boolean isFavorited = articleRepository.isFavorited(userId, article.getId());
                    int favoriteCount = articleRepository.countFavorite(article.getId());
                    User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                    List<TagV> tags = tagRepository.findByArticleId(article.getId());

                    return new ForResponseArticle(new ArticleForResponse(
                            article.getSlug(),
                            article.getTitle(),
                            article.getDescription(),
                            article.getBody(),
                            tags.stream().map(TagV::getName).collect(Collectors.toList()),
                            ISO8601.format(article.getCreatedAt()),
                            ISO8601.format(article.getUpdatedAt()),
                            isFavorited,
                            favoriteCount,
                            convertUserToProfile(Optional.of(author))
                    ));
                });
    }

    @Transactional
    public Optional<ForResponseArticle> updateArticleBySlug(String slug, Long userId, ArticleUpdated articleUpdated) {
        return articleRepository.findBySlug(slug)
                .map(article -> {
                    Article updated = updateArticle(article, articleUpdated);
                    Article savedArticle = articleRepository.save(updated);
                    boolean isFavorited = articleRepository.isFavorited(userId, savedArticle.getId());
                    int favoriteCount = articleRepository.countFavorite(savedArticle.getId());
                    User author = userRepository.findById(savedArticle.getAuthorId()).orElseThrow();
                    List<TagV> tags = tagRepository.findByArticleId(savedArticle.getId());

                    return new ForResponseArticle(new ArticleForResponse(
                            savedArticle.getSlug(),
                            savedArticle.getTitle(),
                            savedArticle.getDescription(),
                            savedArticle.getBody(),
                            tags.stream().map(TagV::getName).collect(Collectors.toList()),
                            ISO8601.format(savedArticle.getCreatedAt()),
                            ISO8601.format(savedArticle.getUpdatedAt()),
                            isFavorited,
                            favoriteCount,
                            convertUserToProfile(Optional.of(author))
                    ));
                });
    }

    @Transactional
    public void deleteArticleBySlug(String slug) {
        articleRepository.deleteBySlug(slug);
    }

    @Transactional
    public Optional<ForResponseArticle> favoriteArticle(Long userId, String slug) {
        return articleRepository.findBySlug(slug)
                .map(article -> {
                    articleRepository.addFavorite(userId, article.getId());
                    int favoriteCount = articleRepository.countFavorite(article.getId());
                    User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                    List<TagV> tags = tagRepository.findByArticleId(article.getId());

                    return new ForResponseArticle(new ArticleForResponse(
                            article.getSlug(),
                            article.getTitle(),
                            article.getDescription(),
                            article.getBody(),
                            tags.stream().map(TagV::getName).collect(Collectors.toList()),
                            ISO8601.format(article.getCreatedAt()),
                            ISO8601.format(article.getUpdatedAt()),
                            true,
                            favoriteCount + 1,
                            new Profile(author.getUsername(), author.getBio(), author.getImage(), false)
                    ));
                });
    }

    @Transactional
    public Optional<ForResponseArticle> unFavoriteArticle(Long userId, String slug) {
        return articleRepository.findBySlug(slug)
                .map(article -> {
                    articleRepository.removeFavorite(userId, article.getId());
                    int favoriteCount = articleRepository.countFavorite(article.getId());
                    User author = userRepository.findById(article.getAuthorId()).orElseThrow();
                    List<TagV> tags = tagRepository.findByArticleId(article.getId());

                    return new ForResponseArticle(new ArticleForResponse(
                            article.getSlug(),
                            article.getTitle(),
                            article.getDescription(),
                            article.getBody(),
                            tags.stream().map(TagV::getName).collect(Collectors.toList()),
                            ISO8601.format(article.getCreatedAt()),
                            ISO8601.format(article.getUpdatedAt()),
                            false,
                            favoriteCount,
                            new Profile(author.getUsername(), author.getBio(), author.getImage(), false)
                    ));
                });
    }

    private Article updateArticle(Article article, ArticleUpdated update) {
        String title = update.getTitle().orElse(article.getTitle());
        String slug = slugify(title);
        String description = update.getDescription().orElse(article.getDescription());
        String body = update.getBody().orElse(article.getBody());

        article.setTitle(title);
        article.setSlug(slug);
        article.setDescription(description);
        article.setBody(body);
        return article;
    }

    private List<TagV> createTags(List<String> tagNames) {
        List<TagV> existingTags = tagRepository.findByNames(tagNames);
        List<TagV> newTags = extractNewTags(tagNames, existingTags);
        return Stream.concat(existingTags.stream(), newTags.stream())
                .collect(Collectors.toList());
    }

    private void connectTagArticle(List<TagV> tags, Long articleId) {
        List<ArticleTag> articleTags = tags.stream()
                .map(tag -> new ArticleTag(-1L, articleId, tag.getId()))
                .collect(Collectors.toList());
        articleRepository.saveArticleTags(articleTags);
    }

    private List<TagV> extractNewTags(List<String> tagNames, List<TagV> existingTags) {
        Set<String> existingTagNames = existingTags.stream()
                .map(TagV::getName)
                .collect(Collectors.toSet());
        
        return tagNames.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(TagV::create)
                .collect(Collectors.toList());
    }

    private Optional<ForResponseArticle> getArticleResponse(Article article, List<TagV> tags, Optional<Long> currentUserId) {
        return userRepository.findById(article.getAuthorId())
                .map(author -> getArticleWithTags(article, author, tags, currentUserId));
    }

    private ForResponseArticle getArticleWithTags(Article article, User author, List<TagV> tags, Optional<Long> currentUserId) {
        boolean isFavorited = currentUserId
                .map(userId -> articleRepository.isFavorited(userId, article.getId()))
                .orElse(false);
        
        int favoriteCount = articleRepository.countFavorite(article.getId());

        return new ForResponseArticle(new ArticleForResponse(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                tags.stream().map(TagV::getName).collect(Collectors.toList()),
                ISO8601.format(article.getCreatedAt()),
                ISO8601.format(article.getUpdatedAt()),
                isFavorited,
                favoriteCount,
                new Profile(author.getUsername(), author.getBio(), author.getImage(), false)
        ));
    }

    private Profile convertUserToProfile(Optional<User> author) {
        return author
                .map(a -> new Profile(a.getUsername(), a.getBio(), a.getImage(), false))
                .orElse(new Profile("", Optional.empty(), Optional.empty(), false));
    }
}