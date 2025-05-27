package realworld.com.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(length = 1024)
    private String bio;
    
    private String image;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "author")
    private Set<Article> articles = new HashSet<>();
    
    @OneToMany(mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "followers",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "followee_id")
    )
    private Set<User> following = new HashSet<>();
    
    @ManyToMany(mappedBy = "following")
    private Set<User> followers = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "favorite",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "favorited_id")
    )
    private Set<Article> favoriteArticles = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getBio() {
        return bio;
    }
    
    public void setBio(String bio) {
        this.bio = bio;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Set<Article> getArticles() {
        return articles;
    }
    
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
    
    public Set<Comment> getComments() {
        return comments;
    }
    
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
    
    public Set<User> getFollowing() {
        return following;
    }
    
    public void setFollowing(Set<User> following) {
        this.following = following;
    }
    
    public Set<User> getFollowers() {
        return followers;
    }
    
    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }
    
    public Set<Article> getFavoriteArticles() {
        return favoriteArticles;
    }
    
    public void setFavoriteArticles(Set<Article> favoriteArticles) {
        this.favoriteArticles = favoriteArticles;
    }
    
    public void follow(User user) {
        this.following.add(user);
        user.getFollowers().add(this);
    }
    
    public void unfollow(User user) {
        this.following.remove(user);
        user.getFollowers().remove(this);
    }
    
    public boolean isFollowing(User user) {
        return this.following.contains(user);
    }
    
    public void favoriteArticle(Article article) {
        this.favoriteArticles.add(article);
        article.getFavoritedBy().add(this);
    }
    
    public void unfavoriteArticle(Article article) {
        this.favoriteArticles.remove(article);
        article.getFavoritedBy().remove(this);
    }
    
    public boolean hasFavorited(Article article) {
        return this.favoriteArticles.contains(article);
    }
}