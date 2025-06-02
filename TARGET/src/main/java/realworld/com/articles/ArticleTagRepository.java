package realworld.com.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for ArticleTagEntity.
 */
@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTagEntity, Long> {
    
    /**
     * Find all article-tag relationships for a specific article
     * 
     * @param articleId the article ID
     * @return list of article-tag relationships
     */
    List<ArticleTagEntity> findByArticleId(Long articleId);
    
    /**
     * Find all article-tag relationships for a collection of articles
     * 
     * @param articleIds collection of article IDs
     * @return list of article-tag relationships
     */
    List<ArticleTagEntity> findByArticleIdIn(Collection<Long> articleIds);
    
    /**
     * Custom query to join article tags with tag information
     * 
     * @param articleId the article ID
     * @return list of tags for the article
     */
    @Query("SELECT t FROM TagEntity t JOIN ArticleTagEntity at ON t.id = at.tagId WHERE at.articleId = :articleId")
    List<TagEntity> findTagsByArticleId(@Param("articleId") Long articleId);
    
    /**
     * Custom query to join article tags with tag information for multiple articles
     * 
     * @param articleIds collection of article IDs
     * @return list of article IDs and their associated tags
     */
    @Query("SELECT at.articleId, t FROM TagEntity t JOIN ArticleTagEntity at ON t.id = at.tagId WHERE at.articleId IN :articleIds")
    List<Object[]> findTagsByArticleIdIn(@Param("articleIds") Collection<Long> articleIds);
}