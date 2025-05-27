package realworld.com.model;

import jakarta.persistence.*;

/**
 * This entity represents the join table between Article and Tag.
 * While JPA can handle many-to-many relationships automatically,
 * this entity is provided for compatibility with the existing schema
 * that has an id column in the join table.
 */
@Entity
@Table(name = "articles_tags")
public class ArticleTag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;
    
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
    
    // Constructors
    public ArticleTag() {
    }
    
    public ArticleTag(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Article getArticle() {
        return article;
    }
    
    public void setArticle(Article article) {
        this.article = article;
    }
    
    public Tag getTag() {
        return tag;
    }
    
    public void setTag(Tag tag) {
        this.tag = tag;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleTag)) return false;
        
        ArticleTag that = (ArticleTag) o;
        
        if (article != null ? !article.equals(that.article) : that.article != null) return false;
        return tag != null ? tag.equals(that.tag) : that.tag == null;
    }
    
    @Override
    public int hashCode() {
        int result = article != null ? article.hashCode() : 0;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}