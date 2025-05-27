package com.realworld.articles.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentStorage extends JpaRepository<Comment, Long> {
    
    /**
     * Creates a new comment
     * @param comment the comment to create
     * @return the created comment
     */
    default Comment createComment(Comment comment) {
        return save(comment);
    }

    /**
     * Gets all comments for an article
     * @param articleId the article id
     * @return list of comments
     */
    List<Comment> findByArticleId(Long articleId);

    /**
     * Deletes a comment by its id
     * @param commentId the comment id to delete
     */
    void deleteById(Long commentId);
}