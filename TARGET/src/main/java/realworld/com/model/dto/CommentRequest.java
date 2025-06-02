package realworld.com.model.dto;

/**
 * DTO for comment requests in the API
 */
public class CommentRequest {
    private CommentForJson comment;

    // Default constructor
    public CommentRequest() {
    }

    public CommentRequest(CommentForJson comment) {
        this.comment = comment;
    }

    // Getters and Setters
    public CommentForJson getComment() {
        return comment;
    }

    public void setComment(CommentForJson comment) {
        this.comment = comment;
    }
}