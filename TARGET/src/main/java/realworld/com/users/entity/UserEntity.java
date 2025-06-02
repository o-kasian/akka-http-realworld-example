package realworld.com.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import realworld.com.articles.comments.entity.CommentEntity;
import realworld.com.articles.entity.ArticleEntity;
import realworld.com.articles.entity.FavoriteEntity;
import realworld.com.profile.entity.UserFollowerEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
    @UniqueConstraint(name = "user_username_unique", columnNames = "username")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String email;
    
    @Column(length = 1024)
    private String bio;
    
    private String image;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    // Relationships
    
    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleEntity> articles = new ArrayList<>();
    
    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteEntity> favorites = new ArrayList<>();
    
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFollowerEntity> following = new ArrayList<>();
    
    @OneToMany(mappedBy = "followeeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFollowerEntity> followers = new ArrayList<>();
    
    // Validation logic from Scala's require statements
    @PrePersist
    @PreUpdate
    public void validate() {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username.empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password.empty");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("email.empty");
        }
    }
}
