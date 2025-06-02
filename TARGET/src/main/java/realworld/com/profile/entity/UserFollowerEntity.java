package realworld.com.profile.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import realworld.com.users.entity.UserEntity;

import java.sql.Timestamp;

@Entity
@Table(name = "followers", uniqueConstraints = {
    @UniqueConstraint(name = "follower_follower_follwed_unq", columnNames = {"user_id", "followee_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowerEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity follower;
    
    @Column(name = "followee_id", nullable = false, insertable = false, updatable = false)
    private Long followeeId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    private UserEntity followee;
    
    @Column(name = "inserted_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp insertedAt;
    
    // Constructor matching the Scala case class
    public UserFollowerEntity(Long userId, Long followeeId, Timestamp insertedAt) {
        this.userId = userId;
        this.followeeId = followeeId;
        this.insertedAt = insertedAt;
    }
}