package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import realworld.com.articles.comments.entity.Comment;
import realworld.com.users.entity.UserEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles", uniqueConstraints = {
    @UniqueConstraint(name = "articles_slug_unique", columnNames = "slug")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String slug;
    
    @Column(nullable = false, length = 300)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;
    
    @Column(name = "author_id", nullable = false, insertable = false, updatable = false)
    private Long authorId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    // Relationships
    
    @OneToMany(mappedBy = "articleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(mappedBy = "articleId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();
    
    @OneToMany(mappedBy = "favoritedId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favoritedBy = new ArrayList<>();
    
    /**
     * Utility method to create a slug from a title
     * @param title The article title
     * @return A URL-friendly slug
     */
    public static String slugify(String title) {
        return title.toLowerCase().replaceAll("\\s", "-");
    }
}