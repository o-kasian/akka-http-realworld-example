package realworld.com.profile;

import java.util.Objects;

public class Profile {
    
    private String username;
    private String bio;
    private String image;
    private boolean following;
    
    public Profile() {
    }
    
    public Profile(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }
    
    // Getters and Setters
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return following == profile.following &&
               Objects.equals(username, profile.username) &&
               Objects.equals(bio, profile.bio) &&
               Objects.equals(image, profile.image);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(username, bio, image, following);
    }
}