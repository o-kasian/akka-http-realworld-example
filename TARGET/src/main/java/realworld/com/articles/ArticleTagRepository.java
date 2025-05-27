package realworld.com.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    
    List<ArticleTag> findByArticle(Article article);
    
    void deleteByArticleAndTag(Article article, Tag tag);
}