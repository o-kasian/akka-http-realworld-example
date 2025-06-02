package realworld.com.model.dto;

import realworld.com.model.Profile;

/**
 * DTO for comment responses in the API
 */
public class CommentData {
    private Long id;
    private String createdAt;
    private String updatedAt;
    private String body;
    private String username;
    private Profile author;

    // Default constructor
    public CommentData() {
    }

    public CommentData(Long id, String createdAt, String updatedAt, String body, String username, Profile author) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.body = body;
        this.username = username;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Profile getAuthor() {
        return author;
    }

    public void setAuthor(Profile author) {
        this.author = author;
    }
}