package realworld.com.users.model;

public class UserWithToken {
    private String username;
    private String email;
    private String bio;
    private String image;
    private String token;

    public UserWithToken() {}

    public UserWithToken(String username, String email, String bio, String image, String token) {
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}