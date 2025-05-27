package realworld.com.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import realworld.com.users.User;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    
    Optional<Favorite> findByUserAndArticle(User user, Article article);
    
    boolean existsByUserAndArticle(User user, Article article);
    
    int countByArticle(Article article);
}