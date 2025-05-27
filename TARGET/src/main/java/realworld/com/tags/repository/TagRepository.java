package realworld.com.tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import realworld.com.articles.model.Tag;
import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t JOIN ArticleTag at ON t.id = at.tagId WHERE at.articleId IN :articleIds")
    List<Tag> findTagsByArticleIds(List<Long> articleIds);

    @Query("SELECT t FROM Tag t JOIN ArticleTag at ON t.id = at.tagId WHERE at.articleId = :articleId")
    List<Tag> findTagsByArticleId(Long articleId);

    List<Tag> findByNameIn(List<String> tagNames);
}