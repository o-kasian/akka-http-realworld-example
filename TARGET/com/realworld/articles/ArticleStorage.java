package com.realworld.articles;

import com.realworld.articles.comments.Comment;
import com.realworld.articles.dto.ArticleRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleStorage extends JpaRepository<Article, Long> {
    
    List<Article> findArticlesByFilters(ArticleRequest pageRequest);
    
    @Query("SELECT a FROM Article a JOIN UserFollowers f ON f.followeeId = a.id " +
           "WHERE f.userId = :userId ORDER BY a.createdAt DESC")
    List<Article> findArticlesByFollowees(Long userId, PageRequest pageRequest);
    
    Optional<Article> findBySlug(String slug);
    
    @Query("SELECT f.favoritedId FROM Favorite f WHERE f.userId = :userId AND f.favoritedId IN :articleIds")
    List<Long> findFavoriteArticleIds(Long userId, List<Long> articleIds);
    
    @Query("SELECT f.favoritedId, COUNT(f) FROM Favorite f WHERE f.favoritedId IN :articleIds " +
           "GROUP BY f.favoritedId")
    List<Object[]> countFavorites(List<Long> articleIds);
    
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.favoritedId = :articleId")
    int countFavorite(Long articleId);
    
    @Modifying
    @Transactional
    @Query("INSERT INTO ArticleTag (articleId, tagId) VALUES (:#{#tags.articleId}, :#{#tags.tagId})")
    List<ArticleTag> insertArticleTags(List<ArticleTag> tags);
    
    @Modifying
    @Transactional
    default void deleteArticleBySlugCascade(String slug) {
        Article article = findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        
        // Delete related entities
        deleteArticleTagsByArticleId(article.getId());
        deleteFavoritesByArticleId(article.getId());
        deleteCommentsByArticleId(article.getId());
        delete(article);
    }
    
    @Modifying
    @Query("DELETE FROM ArticleTag at WHERE at.articleId = :articleId")
    void deleteArticleTagsByArticleId(Long articleId);
    
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.favoritedId = :articleId")
    void deleteFavoritesByArticleId(Long articleId);
    
    @Modifying
    @Query("DELETE FROM Comment c WHERE c.articleId = :articleId")
    void deleteCommentsByArticleId(Long articleId);
    
    @Modifying
    @Transactional
    default Favorite favoriteArticle(Long userId, Long articleId) {
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setFavoritedId(articleId);
        return saveFavorite(favorite);
    }
    
    @Query("SELECT f FROM Favorite f WHERE f.userId = :userId AND f.favoritedId = :articleId")
    Optional<Favorite> findFavorite(Long userId, Long articleId);
    
    Favorite saveFavorite(Favorite favorite);
    
    @Modifying
    @Transactional
    default int unfavoriteArticle(Long userId, Long articleId) {
        return deleteFavoriteByUserIdAndArticleId(userId, articleId);
    }
    
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.userId = :userId AND f.favoritedId = :articleId")
    int deleteFavoriteByUserIdAndArticleId(Long userId, Long articleId);
}