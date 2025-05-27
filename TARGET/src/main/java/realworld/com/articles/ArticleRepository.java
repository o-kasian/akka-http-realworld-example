package realworld.com.articles;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import realworld.com.users.User;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    
    Optional<Article> findBySlug(String slug);
    
    Page<Article> findByAuthor(User author, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.name = :tag")
    Page<Article> findByTag(@Param("tag") String tag, Pageable pageable);
    
    @Query("SELECT a FROM Article a JOIN a.favoritedBy u WHERE u = :user")
    Page<Article> findByFavoritedBy(@Param("user") User user, Pageable pageable);
    
    @Query("SELECT a FROM Article a WHERE a.author IN :authors")
    Page<Article> findByAuthors(@Param("authors") List<User> authors, Pageable pageable);
}