package realworld.com.articles.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import realworld.com.articles.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findBySlug(String slug);
    
    void deleteBySlug(String slug);
    
    @Query("SELECT a FROM Article a WHERE a.authorId IN " +
           "(SELECT f.followedId FROM Follow f WHERE f.followerId = :userId)")
    List<Article> findArticlesByFollowees(Long userId);
}