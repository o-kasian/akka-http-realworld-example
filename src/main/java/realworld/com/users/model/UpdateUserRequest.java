package realworld.com.users.model;

import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {
    @NotNull
    private UserUpdate user;

    public UpdateUserRequest() {}

    public UpdateUserRequest(UserUpdate user) {
        this.user = user;
    }

    public UserUpdate getUser() {
        return user;
    }

    public void setUser(UserUpdate user) {
        this.user = user;
    }
}