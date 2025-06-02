package realworld.com.articles;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * JPA Entity for articles_tags table.
 */
@Entity
@Table(name = "articles_tags")
public class ArticleTagEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "article_id", nullable = false)
    private Long articleId;
    
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    /**
     * Default constructor required by JPA
     */
    public ArticleTagEntity() {
    }

    /**
     * Constructor with articleId and tagId
     * 
     * @param articleId the article ID
     * @param tagId     the tag ID
     */
    public ArticleTagEntity(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }

    /**
     * Constructor with all fields
     * 
     * @param id        the relationship ID
     * @param articleId the article ID
     * @param tagId     the tag ID
     */
    public ArticleTagEntity(Long id, Long articleId, Long tagId) {
        this.id = id;
        this.articleId = articleId;
        this.tagId = tagId;
    }

    /**
     * Gets the relationship ID
     * 
     * @return the relationship ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the relationship ID
     * 
     * @param id the relationship ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the article ID
     * 
     * @return the article ID
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * Sets the article ID
     * 
     * @param articleId the article ID
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    /**
     * Gets the tag ID
     * 
     * @return the tag ID
     */
    public Long getTagId() {
        return tagId;
    }

    /**
     * Sets the tag ID
     * 
     * @param tagId the tag ID
     */
    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    /**
     * Converts this entity to an ArticleTag model
     * 
     * @return an ArticleTag instance
     */
    public ArticleTag toArticleTag() {
        return new ArticleTag(id, articleId, tagId);
    }

    /**
     * Creates an ArticleTagEntity from an ArticleTag model
     * 
     * @param articleTag the ArticleTag model
     * @return an ArticleTagEntity instance
     */
    public static ArticleTagEntity fromArticleTag(ArticleTag articleTag) {
        return new ArticleTagEntity(
                articleTag.getId(),
                articleTag.getArticleId(),
                articleTag.getTagId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleTagEntity that = (ArticleTagEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(articleId, that.articleId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, tagId);
    }

    @Override
    public String toString() {
        return "ArticleTagEntity{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagId=" + tagId +
                '}';
    }
}