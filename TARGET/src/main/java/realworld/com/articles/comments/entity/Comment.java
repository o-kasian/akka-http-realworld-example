package realworld.com.articles.comments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 4096)
    private String body;
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;
    
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    
    /**
     * Factory method to create a new comment
     * @param body The comment text
     * @param articleId The ID of the article being commented on
     * @param authorId The ID of the user creating the comment
     * @return A new Comment instance
     */
    public static Comment create(String body, Long articleId, Long authorId) {
        Comment comment = new Comment();
        comment.setId(-1L); // To be assigned by the database
        comment.setBody(body);
        comment.setArticleId(articleId);
        comment.setAuthorId(authorId);
        
        Timestamp now = new Timestamp(new Date().getTime());
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);
        
        return comment;
    }
}