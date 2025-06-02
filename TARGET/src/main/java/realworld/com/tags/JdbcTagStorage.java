package realworld.com.tags;

import org.springframework.stereotype.Repository;
import realworld.com.articles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of TagStorage using Spring Data JPA repositories.
 */
@Repository
public class JdbcTagStorage implements TagStorage {
    
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    
    /**
     * Constructor with repositories
     * 
     * @param tagRepository        repository for tags
     * @param articleTagRepository repository for article-tag relationships
     */
    public JdbcTagStorage(TagRepository tagRepository, ArticleTagRepository articleTagRepository) {
        this.tagRepository = tagRepository;
        this.articleTagRepository = articleTagRepository;
    }
    
    @Override
    public CompletableFuture<List<TagV>> getTags() {
        return CompletableFuture.supplyAsync(() -> 
            tagRepository.findAll().stream()
                .map(TagEntity::toTagV)
                .collect(Collectors.toList())
        );
    }
    
    @Override
    public CompletableFuture<List<ArticleTagPair>> getTagsByArticles(List<Long> articleIds) {
        return CompletableFuture.supplyAsync(() -> {
            List<Object[]> results = articleTagRepository.findTagsByArticleIdIn(articleIds);
            List<ArticleTagPair> pairs = new ArrayList<>();
            
            for (Object[] result : results) {
                Long articleId = (Long) result[0];
                TagEntity tagEntity = (TagEntity) result[1];
                pairs.add(new ArticleTagPair(articleId, tagEntity.toTagV()));
            }
            
            return pairs;
        });
    }
    
    @Override
    public CompletableFuture<List<TagV>> getTagsByArticle(Long articleId) {
        return CompletableFuture.supplyAsync(() -> 
            articleTagRepository.findTagsByArticleId(articleId).stream()
                .map(TagEntity::toTagV)
                .collect(Collectors.toList())
        );
    }
    
    @Override
    public CompletableFuture<List<TagV>> findTagByNames(List<String> tagNames) {
        return CompletableFuture.supplyAsync(() -> 
            tagRepository.findByNameIn(tagNames).stream()
                .map(TagEntity::toTagV)
                .collect(Collectors.toList())
        );
    }
    
    @Override
    public CompletableFuture<List<TagV>> insertAndGet(List<TagV> tagVs) {
        return CompletableFuture.supplyAsync(() -> {
            // Convert TagV to TagEntity
            List<TagEntity> entities = tagVs.stream()
                .map(tagV -> new TagEntity(tagV.getName()))
                .collect(Collectors.toList());
            
            // Save all entities
            List<TagEntity> savedEntities = tagRepository.saveAll(entities);
            
            // Convert back to TagV
            return savedEntities.stream()
                .map(TagEntity::toTagV)
                .collect(Collectors.toList());
        });
    }
}