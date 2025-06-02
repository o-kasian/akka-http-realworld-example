package realworld.com.tags;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * REST controller for tag-related endpoints.
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    
    private final TagService tagService;
    
    /**
     * Constructor with tag service
     * 
     * @param tagService service for tag operations
     */
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    
    /**
     * Get all tags
     * 
     * @return CompletableFuture with ResponseEntity containing all tags
     */
    @GetMapping
    public CompletableFuture<ResponseEntity<ResponseTags>> getTags() {
        return tagService.getTags()
                .thenApply(ResponseEntity::ok);
    }
}