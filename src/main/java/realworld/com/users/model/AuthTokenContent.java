package realworld.com.users.model;

public class AuthTokenContent {
    private final Long userId;

    public AuthTokenContent(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}