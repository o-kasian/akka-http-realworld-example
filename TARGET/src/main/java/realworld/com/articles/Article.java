package realworld.com.articles;

import jakarta.persistence.*;
import realworld.com.users.User;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String slug;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
    
    @ManyToMany
    @JoinTable(
        name = "articles_tags",
        joinColumns = @JoinColumn(name = "article_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    @ManyToMany
    @JoinTable(
        name = "favorite",
        joinColumns = @JoinColumn(name = "favorited_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> favoritedBy = new HashSet<>();
    
    // Default constructor required by JPA
    public Article() {
    }
    
    public Article(Long id, String slug, String title, String description, String body, User author, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public User getAuthor() {
        return author;
    }
    
    public void setAuthor(User author) {
        this.author = author;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Set<Tag> getTags() {
        return tags;
    }
    
    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    
    public Set<User> getFavoritedBy() {
        return favoritedBy;
    }
    
    public void setFavoritedBy(Set<User> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }
    
    public void addTag(Tag tag) {
        tags.add(tag);
    }
    
    public void removeTag(Tag tag) {
        tags.remove(tag);
    }
    
    public void addToFavorites(User user) {
        favoritedBy.add(user);
    }
    
    public void removeFromFavorites(User user) {
        favoritedBy.remove(user);
    }
    
    public boolean isFavoritedBy(User user) {
        return favoritedBy.contains(user);
    }
    
    public int getFavoritesCount() {
        return favoritedBy.size();
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}