package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles_tags", uniqueConstraints = {
    @UniqueConstraint(name = "article_tag_id_unique", columnNames = {"article_id", "tag_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "article_id", nullable = false, insertable = false, updatable = false)
    private Long articleId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private ArticleEntity article;
    
    @Column(name = "tag_id", nullable = false, insertable = false, updatable = false)
    private Long tagId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tag;
    
    // Constructor matching the Scala case class
    public ArticleTagEntity(Long id, Long articleId, Long tagId) {
        this.id = id;
        this.articleId = articleId;
        this.tagId = tagId;
    }
}