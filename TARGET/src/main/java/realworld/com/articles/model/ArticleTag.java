package realworld.com.articles.model;

import jakarta.persistence.*;

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

    public ArticleTag() {
    }

    public ArticleTag(Long id, Long articleId, Long tagId) {
        this.id = id;
        this.articleId = articleId;
        this.tagId = tagId;
    }

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