package realworld.com.tags;

import realworld.com.articles.TagV;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for tag storage operations.
 */
public interface TagStorage {
    
    /**
     * Get all tags
     * 
     * @return CompletableFuture with a list of all tags
     */
    CompletableFuture<List<TagV>> getTags();
    
    /**
     * Get tags for multiple articles
     * 
     * @param articleIds list of article IDs
     * @return CompletableFuture with a list of article ID and tag pairs
     */
    CompletableFuture<List<ArticleTagPair>> getTagsByArticles(List<Long> articleIds);
    
    /**
     * Get tags for a specific article
     * 
     * @param articleId the article ID
     * @return CompletableFuture with a list of tags for the article
     */
    CompletableFuture<List<TagV>> getTagsByArticle(Long articleId);
    
    /**
     * Find tags by their names
     * 
     * @param tagNames list of tag names
     * @return CompletableFuture with a list of matching tags
     */
    CompletableFuture<List<TagV>> findTagByNames(List<String> tagNames);
    
    /**
     * Insert tags and return the inserted tags with their IDs
     * 
     * @param tagVs list of tags to insert
     * @return CompletableFuture with a list of inserted tags
     */
    CompletableFuture<List<TagV>> insertAndGet(List<TagV> tagVs);
    
    /**
     * Class to represent an article ID and tag pair
     */
    class ArticleTagPair {
        private final Long articleId;
        private final TagV tag;
        
        public ArticleTagPair(Long articleId, TagV tag) {
            this.articleId = articleId;
            this.tag = tag;
        }
        
        public Long getArticleId() {
            return articleId;
        }
        
        public TagV getTag() {
            return tag;
        }
    }
}