package realworld.com.articles.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import realworld.com.articles.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameIn(List<String> names);
    
    @Query("SELECT t FROM Tag t JOIN ArticleTag at ON t.id = at.tagId WHERE at.articleId = :articleId")
    List<Tag> findTagsByArticleId(Long articleId);
    
    @Query("SELECT at.articleId, t FROM Tag t JOIN ArticleTag at ON t.id = at.tagId WHERE at.articleId IN :articleIds")
    List<Object[]> findTagsByArticleIds(List<Long> articleIds);
}