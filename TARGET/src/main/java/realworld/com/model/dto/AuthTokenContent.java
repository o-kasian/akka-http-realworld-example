package realworld.com.model.dto;

/**
 * DTO for JWT token content
 */
public class AuthTokenContent {
    private Long userId;

    // Default constructor
    public AuthTokenContent() {
    }

    public AuthTokenContent(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}