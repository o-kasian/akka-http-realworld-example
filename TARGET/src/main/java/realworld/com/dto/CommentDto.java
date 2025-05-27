package realworld.com.dto;

public class CommentDto {
    private Long id;
    private String body;
    private String createdAt;
    private String updatedAt;
    private ProfileDto author;
    
    public CommentDto() {
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public ProfileDto getAuthor() {
        return author;
    }
    
    public void setAuthor(ProfileDto author) {
        this.author = author;
    }
}