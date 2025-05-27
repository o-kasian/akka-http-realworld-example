package realworld.com.users.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Timestamp;
import java.util.Date;

public class UserRegistration {
    private String username;
    private String password;
    private String email;

    public UserRegistration() {}

    public UserRegistration(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        Timestamp now = new Timestamp(new Date().getTime());
        return new User(
            0L,
            username,
            passwordEncoder.encode(password),
            email,
            null,
            null,
            now,
            now
        );
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}