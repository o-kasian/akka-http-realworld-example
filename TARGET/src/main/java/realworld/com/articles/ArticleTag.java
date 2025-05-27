package realworld.com.articles;

import jakarta.persistence.*;

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
    
    // Default constructor required by JPA
    public ArticleTag() {
    }
    
    public ArticleTag(Long id, Article article, Tag tag) {
        this.id = id;
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
}