package realworld.com.users.model;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull
    private UserRegistration user;

    public RegisterRequest() {}

    public RegisterRequest(UserRegistration user) {
        this.user = user;
    }

    public UserRegistration getUser() {
        return user;
    }

    public void setUser(UserRegistration user) {
        this.user = user;
    }
}