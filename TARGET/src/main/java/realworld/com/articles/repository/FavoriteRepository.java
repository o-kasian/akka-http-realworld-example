package realworld.com.articles.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import realworld.com.articles.model.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndFavoritedId(Long userId, Long articleId);
    
    void deleteByUserIdAndFavoritedId(Long userId, Long articleId);
    
    @Query("SELECT f.favoritedId FROM Favorite f WHERE f.userId = :userId AND f.favoritedId IN :articleIds")
    List<Long> findFavoritedArticleIds(Long userId, List<Long> articleIds);
    
    @Query("SELECT f.favoritedId, COUNT(f) FROM Favorite f WHERE f.favoritedId IN :articleIds GROUP BY f.favoritedId")
    List<Object[]> countFavoritesByArticleIds(List<Long> articleIds);
    
    @Query("SELECT COUNT(f) FROM Favorite f WHERE f.favoritedId = :articleId")
    int countByFavoritedId(Long articleId);
}