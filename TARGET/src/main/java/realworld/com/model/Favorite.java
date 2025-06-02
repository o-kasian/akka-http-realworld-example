package realworld.com.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "favorited_id")
    private Long favoritedId;

    @Column(name = "created_at")
    private Instant createdAt;

    // Default constructor required by JPA
    public Favorite() {
    }

    public Favorite(Long id, Long userId, Long favoritedId) {
        this.id = id;
        this.userId = userId;
        this.favoritedId = favoritedId;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    // Getters and Setters
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}