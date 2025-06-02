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
public class ArticleTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;
    
    @Column(name = "tag_id", nullable = false)
    private Long tagId;
}