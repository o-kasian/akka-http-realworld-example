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
    private Article favorited;
    
    // Constructors
    public Favorite() {
    }
    
    public Favorite(User user, Article favorited) {
        this.user = user;
        this.favorited = favorited;
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
    
    public Article getFavorited() {
        return favorited;
    }
    
    public void setFavorited(Article favorited) {
        this.favorited = favorited;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Favorite)) return false;
        
        Favorite favorite = (Favorite) o;
        
        if (user != null ? !user.equals(favorite.user) : favorite.user != null) return false;
        return favorited != null ? favorited.equals(favorite.favorited) : favorite.favorited == null;
    }
    
    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (favorited != null ? favorited.hashCode() : 0);
        return result;
    }
}