package realworld.com.model.dto;

import realworld.com.model.Profile;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for article responses in the API
 */
public class ArticleForResponse {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList = new ArrayList<>();
    private String createdAt;
    private String updatedAt;
    private boolean favorited;
    private int favoritesCount;
    private Profile author;

    // Default constructor
    public ArticleForResponse() {
    }

    public ArticleForResponse(String slug, String title, String description, String body, 
                             List<String> tagList, String createdAt, String updatedAt, 
                             boolean favorited, int favoritesCount, Profile author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.author = author;
    }

    // Helper method to format Instant to ISO-8601 string
    public static String formatInstant(Instant instant) {
        return DateTimeFormatter.ISO_INSTANT.format(instant);
    }

    // Getters and Setters
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

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}