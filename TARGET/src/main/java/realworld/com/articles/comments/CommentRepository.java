package realworld.com.articles.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import realworld.com.articles.Article;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
    List<Comment> findByArticleOrderByCreatedAtDesc(Article article);
}