package realworld.com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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

    // Getters and setters
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        Favorite favorite = (Favorite) o;
        return user.getId().equals(favorite.user.getId()) &&
               article.getId().equals(favorite.article.getId());
    }

    @Override
    public int hashCode() {
        return 31 * user.getId().hashCode() + article.getId().hashCode();
    }
}