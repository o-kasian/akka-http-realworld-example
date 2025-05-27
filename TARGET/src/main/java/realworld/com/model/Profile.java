package realworld.com.model;

/**
 * This class represents a user profile view.
 * It's not an entity but a projection of User data.
 */
public class Profile {
    private String username;
    private String bio;
    private String image;
    private boolean following;

    // Default constructor
    public Profile() {
    }

    public Profile(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }

    // Factory method to create a Profile from a User
    public static Profile fromUser(User user, boolean following) {
        return new Profile(
            user.getUsername(),
            user.getBio(),
            user.getImage(),
            following
        );
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}