package realworld.com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * This class represents a composite entity for the followers table.
 * In JPA, we typically handle many-to-many relationships directly with @ManyToMany,
 * but this entity is provided for compatibility with the existing schema that has
 * an inserted_at timestamp column.
 */
@Entity
@Table(name = "followers")
public class UserFollower {
    
    @EmbeddedId
    private UserFollowerId id;
    
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @MapsId("followeeId")
    @JoinColumn(name = "followee_id")
    private User followee;
    
    @Column(name = "inserted_at", nullable = false)
    private LocalDateTime insertedAt;
    
    @PrePersist
    protected void onCreate() {
        this.insertedAt = LocalDateTime.now();
    }
    
    // Constructors
    public UserFollower() {
    }
    
    public UserFollower(User user, User followee) {
        this.user = user;
        this.followee = followee;
        this.id = new UserFollowerId(user.getId(), followee.getId());
        this.insertedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    
    public UserFollowerId getId() {
        return id;
    }
    
    public void setId(UserFollowerId id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public User getFollowee() {
        return followee;
    }
    
    public void setFollowee(User followee) {
        this.followee = followee;
    }
    
    public LocalDateTime getInsertedAt() {
        return insertedAt;
    }
    
    public void setInsertedAt(LocalDateTime insertedAt) {
        this.insertedAt = insertedAt;
    }
}