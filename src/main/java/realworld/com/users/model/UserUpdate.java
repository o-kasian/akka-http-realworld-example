package realworld.com.users.model;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.sql.Timestamp;
import java.util.Date;

public class UserUpdate {
    private String username;
    private String password;
    private String email;
    private String bio;
    private String image;

    public UserUpdate() {}

    public User merge(User user, PasswordEncoder passwordEncoder) {
        return new User(
            user.getId(),
            username != null ? username : user.getUsername(),
            password != null ? passwordEncoder.encode(password) : user.getPassword(),
            email != null ? email : user.getEmail(),
            bio != null ? bio : user.getBio(),
            image != null ? image : user.getImage(),
            user.getCreatedAt(),
            new Timestamp(new Date().getTime())
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}