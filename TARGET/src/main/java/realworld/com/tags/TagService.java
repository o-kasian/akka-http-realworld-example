package realworld.com.tags;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for tag-related operations.
 */
@Service
public class TagService {
    
    private final TagStorage tagStorage;
    
    /**
     * Constructor with tag storage
     * 
     * @param tagStorage storage for tag operations
     */
    public TagService(TagStorage tagStorage) {
        this.tagStorage = tagStorage;
    }
    
    /**
     * Get all tags
     * 
     * @return CompletableFuture with ResponseTags containing all tags
     */
    public CompletableFuture<ResponseTags> getTags() {
        return tagStorage.getTags()
                .thenApply(tags -> new ResponseTags(tags));
    }
}