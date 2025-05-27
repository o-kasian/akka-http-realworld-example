package realworld.com.profile;

import jakarta.persistence.*;
import realworld.com.users.User;
import java.time.Instant;

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
    private Instant insertedAt;
    
    // Default constructor required by JPA
    public UserFollower() {
    }
    
    public UserFollower(User user, User followee, Instant insertedAt) {
        this.id = new UserFollowerId(user.getId(), followee.getId());
        this.user = user;
        this.followee = followee;
        this.insertedAt = insertedAt;
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
    
    public Instant getInsertedAt() {
        return insertedAt;
    }
    
    public void setInsertedAt(Instant insertedAt) {
        this.insertedAt = insertedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        insertedAt = Instant.now();
    }
}