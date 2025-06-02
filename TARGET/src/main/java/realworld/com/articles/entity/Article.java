package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "articles", uniqueConstraints = {
    @UniqueConstraint(name = "articles_slug_unique", columnNames = "slug")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    
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
    
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    /**
     * Utility method to create a slug from a title
     * @param title The article title
     * @return A URL-friendly slug
     */
    public static String slugify(String title) {
        return title.toLowerCase().replaceAll("\\s", "-");
    }
}