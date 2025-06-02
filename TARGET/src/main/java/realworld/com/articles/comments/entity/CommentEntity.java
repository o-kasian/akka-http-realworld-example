package realworld.com.articles.comments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import realworld.com.articles.entity.ArticleEntity;
import realworld.com.users.entity.UserEntity;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 4096)
    private String body;
    
    @Column(name = "article_id", nullable = false, insertable = false, updatable = false)
    private Long articleId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article;
    
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
    
    /**
     * Factory method to create a new comment
     * @param body The comment text
     * @param articleId The ID of the article being commented on
     * @param authorId The ID of the user creating the comment
     * @return A new Comment instance
     */
    public static CommentEntity create(String body, Long articleId, Long authorId) {
        CommentEntity comment = new CommentEntity();
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