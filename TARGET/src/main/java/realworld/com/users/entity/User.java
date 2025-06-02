package realworld.com.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
    @UniqueConstraint(name = "user_username_unique", columnNames = "username")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
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
    
    // Articles written by user
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Article> articles = new HashSet<>();
    
    // Comments written by user
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();
    
    // Articles favorited by user
    @ManyToMany
    @JoinTable(
        name = "user_favorite_articles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<Article> favoritedArticles = new HashSet<>();
    
    // Users that this user follows
    @ManyToMany
    @JoinTable(
        name = "user_follows",
        joinColumns = @JoinColumn(name = "follower_id"),
        inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> following = new HashSet<>();
    
    // Users following this user
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new HashSet<>();
    
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