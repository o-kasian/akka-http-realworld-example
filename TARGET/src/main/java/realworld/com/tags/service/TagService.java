package realworld.com.tags.service;

import org.springframework.stereotype.Service;
import realworld.com.tags.dto.TagsResponse;
import realworld.com.tags.repository.TagRepository;
import realworld.com.articles.model.Tag;
import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagsResponse getTags() {
        List<Tag> tags = tagRepository.findAll();
        return new TagsResponse(tags);
    }
}