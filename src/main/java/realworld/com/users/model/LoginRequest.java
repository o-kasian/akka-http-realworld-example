package realworld.com.users.model;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull
    private LoginUser user;

    public LoginRequest() {}

    public LoginRequest(LoginUser user) {
        this.user = user;
    }

    public LoginUser getUser() {
        return user;
    }

    public void setUser(LoginUser user) {
        this.user = user;
    }

    public static class LoginUser {
        @NotNull
        private String email;
        @NotNull
        private String password;

        public LoginUser() {}

        public LoginUser(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}