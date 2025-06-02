package realworld.com.articles;

import java.util.Objects;

/**
 * Represents a relationship between an article and a tag.
 */
public class ArticleTag {
    private Long id;
    private Long articleId;
    private Long tagId;

    /**
     * Default constructor required by JPA
     */
    public ArticleTag() {
    }

    /**
     * Constructor with all fields
     * 
     * @param id        the relationship ID
     * @param articleId the article ID
     * @param tagId     the tag ID
     */
    public ArticleTag(Long id, Long articleId, Long tagId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleTag that = (ArticleTag) o;
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
        return "ArticleTag{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", tagId=" + tagId +
                '}';
    }
}