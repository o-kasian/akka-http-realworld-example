package realworld.com.model;

import jakarta.persistence.*;

/**
 * This class represents the many-to-many relationship between Articles and Tags.
 * In the database schema, this is represented by the articles_tags table,
 * but in JPA we're using a @ManyToMany relationship in the Article entity.
 * This class is kept for reference but is not actively used in the JPA model.
 */
@Entity
@Table(name = "articles_tags")
public class ArticleTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id", nullable = false)
    private Long articleId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    // Default constructor required by JPA
    public ArticleTag() {
    }

    public ArticleTag(Long id, Long articleId, Long tagId) {
        this.id = id;
        this.articleId = articleId;
        this.tagId = tagId;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}