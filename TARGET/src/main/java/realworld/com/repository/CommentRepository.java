package realworld.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realworld.com.model.Article;
import realworld.com.model.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByArticleOrderByCreatedAtDesc(Article article);
    
    void deleteByArticleId(Long articleId);
}