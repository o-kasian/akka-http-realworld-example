package realworld.com.model.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for creating a new comment
 */
public class CommentForJson {
    @NotBlank(message = "Comment body cannot be empty")
    private String body;

    // Default constructor
    public CommentForJson() {
    }

    public CommentForJson(String body) {
        this.body = body;
    }

    // Getters and Setters
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}