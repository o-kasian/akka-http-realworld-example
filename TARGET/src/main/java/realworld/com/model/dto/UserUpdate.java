package realworld.com.model.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import realworld.com.model.User;

import java.time.Instant;

/**
 * DTO for updating user information
 */
public class UserUpdate {
    private String username;
    private String password;
    private String email;
    private String bio;
    private String image;

    // Default constructor
    public UserUpdate() {
    }

    public UserUpdate(String username, String password, String email, String bio, String image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    // Method to apply updates to an existing user
    public User applyTo(User user) {
        if (username != null) {
            user.setUsername(username);
        }
        if (password != null) {
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (bio != null) {
            user.setBio(bio);
        }
        if (image != null) {
            user.setImage(image);
        }
        user.setUpdatedAt(Instant.now());
        return user;
    }

    // Getters and Setters
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