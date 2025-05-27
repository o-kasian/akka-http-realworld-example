package realworld.com.articles.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "favorited_id", nullable = false)
    private Long favoritedId;

    public Favorite() {
    }

    public Favorite(Long id, Long userId, Long favoritedId) {
        this.id = id;
        this.userId = userId;
        this.favoritedId = favoritedId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFavoritedId() {
        return favoritedId;
    }

    public void setFavoritedId(Long favoritedId) {
        this.favoritedId = favoritedId;
    }
}