package realworld.com.tags.controller;

import org.springframework.web.bind.annotation.*;
import realworld.com.tags.dto.TagsResponse;
import realworld.com.tags.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public TagsResponse getTags() {
        return tagService.getTags();
    }
}