package realworld.com.tags.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realworld.com.tags.dto.TagsResponse;
import realworld.com.tags.model.Tag;
import realworld.com.tags.repository.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Get all tags in the system
     * @return TagsResponse containing a list of tag names
     */
    public TagsResponse getAllTags() {
        List<String> tagNames = tagRepository.findAll()
                .stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        
        return new TagsResponse(tagNames);
    }
}