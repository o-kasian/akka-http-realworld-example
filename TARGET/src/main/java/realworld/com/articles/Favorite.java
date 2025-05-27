package realworld.com.articles;

import jakarta.persistence.*;
import realworld.com.users.User;

@Entity
@Table(name = "favorite")
public class Favorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "favorited_id", nullable = false)
    private Article article;
    
    // Default constructor required by JPA
    public Favorite() {
    }
    
    public Favorite(Long id, User user, Article article) {
        this.id = id;
        this.user = user;
        this.article = article;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Article getArticle() {
        return article;
    }
    
    public void setArticle(Article article) {
        this.article = article;
    }
}