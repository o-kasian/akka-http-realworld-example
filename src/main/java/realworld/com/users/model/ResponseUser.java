package realworld.com.users.model;

public class ResponseUser {
    private UserWithToken user;

    public ResponseUser() {}

    public ResponseUser(UserWithToken user) {
        this.user = user;
    }

    public UserWithToken getUser() {
        return user;
    }

    public void setUser(UserWithToken user) {
        this.user = user;
    }
}