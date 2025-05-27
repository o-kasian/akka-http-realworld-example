package realworld.com.users.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_followers")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "followee_id")
    private Long followeeId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public UserFollower() {}

    public UserFollower(Long userId, Long followeeId, Timestamp createdAt) {
        this.userId = userId;
        this.followeeId = followeeId;
        this.createdAt = createdAt;
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

    public Long getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(Long followeeId) {
        this.followeeId = followeeId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}