package realworld.com.tags.dto;

import realworld.com.articles.model.Tag;
import java.util.List;

public class TagsResponse {
    private List<Tag> tags;

    public TagsResponse() {}

    public TagsResponse(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}