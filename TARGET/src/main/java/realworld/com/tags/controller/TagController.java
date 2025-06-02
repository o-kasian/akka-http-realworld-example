package realworld.com.tags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import realworld.com.tags.dto.TagsResponse;
import realworld.com.tags.service.TagService;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * GET /api/tags
     * Returns a list of all tags in the system
     */
    @GetMapping
    public ResponseEntity<TagsResponse> getTags() {
        TagsResponse response = tagService.getAllTags();
        return ResponseEntity.ok(response);
    }
}