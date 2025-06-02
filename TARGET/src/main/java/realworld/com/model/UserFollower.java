package realworld.com.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "followers")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "followee_id")
    private Long followeeId;

    @Column(name = "inserted_at")
    private Instant insertedAt;

    // Default constructor required by JPA
    public UserFollower() {
    }

    public UserFollower(Long userId, Long followeeId, Instant insertedAt) {
        this.userId = userId;
        this.followeeId = followeeId;
        this.insertedAt = insertedAt;
    }

    @PrePersist
    protected void onCreate() {
        insertedAt = Instant.now();
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

    public Long getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }

    public Instant getInsertedAt() {
        return insertedAt;
    }

    public void setInsertedAt(Instant insertedAt) {
        this.insertedAt = insertedAt;
    }
}