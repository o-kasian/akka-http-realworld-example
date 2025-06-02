package realworld.com.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

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