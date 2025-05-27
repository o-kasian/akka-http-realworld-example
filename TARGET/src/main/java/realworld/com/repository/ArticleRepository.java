package realworld.com.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import realworld.com.model.Article;
import realworld.com.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    Optional<Article> findBySlug(String slug);
    
    Page<Article> findByAuthor(User author, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.name = :tag")
    Page<Article> findByTagName(@Param("tag") String tag, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.favoritedBy u WHERE u.username = :username")
    Page<Article> findByFavoritedUsername(@Param("username") String username, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.author u JOIN u.followers f WHERE f.id = :userId")
    Page<Article> findByFollowedAuthors(@Param("userId") Long userId, Pageable pageable);
    
    boolean existsBySlug(String slug);
}