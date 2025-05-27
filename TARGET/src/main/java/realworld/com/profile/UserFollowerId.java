package realworld.com.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFollowerId implements Serializable {
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "followee_id")
    private Long followeeId;
    
    // Default constructor required by JPA
    public UserFollowerId() {
    }
    
    public UserFollowerId(Long userId, Long followeeId) {
        this.userId = userId;
        this.followeeId = followeeId;
    }
    
    // Getters and Setters
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFollowerId that = (UserFollowerId) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(followeeId, that.followeeId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId, followeeId);
    }
}