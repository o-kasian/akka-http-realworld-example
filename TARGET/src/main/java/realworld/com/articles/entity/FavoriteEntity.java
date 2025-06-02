package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import realworld.com.users.entity.UserEntity;

@Entity
@Table(name = "favorite")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(name = "favorited_id", nullable = false, insertable = false, updatable = false)
    private Long favoritedId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorited_id", nullable = false)
    private ArticleEntity article;
    
    // Constructor matching the Scala case class
    public FavoriteEntity(Long id, Long userId, Long favoritedId) {
        this.id = id;
        this.userId = userId;
        this.favoritedId = favoritedId;
    }
}