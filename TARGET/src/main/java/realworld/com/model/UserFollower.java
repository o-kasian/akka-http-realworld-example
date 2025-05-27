package realworld.com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents the followers relationship between users.
 * In the database schema, this is represented by the followers table,
 * but in JPA we're using a @ManyToMany relationship in the User entity.
 * This class is kept for reference but is not actively used in the JPA model.
 */
@Entity
@Table(name = "followers")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "followee_id", nullable = false)
    private Long followeeId;

    @Column(name = "inserted_at", nullable = false)
    private LocalDateTime insertedAt;

    // Default constructor required by JPA
    public UserFollower() {
    }

    public UserFollower(Long userId, Long followeeId, LocalDateTime insertedAt) {
        this.userId = userId;
        this.followeeId = followeeId;
        this.insertedAt = insertedAt;
    }

    // Getters and setters
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

    public Long getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }

    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }

    @PrePersist
    protected void onCreate() {
        insertedAt = LocalDateTime.now();
    }
}