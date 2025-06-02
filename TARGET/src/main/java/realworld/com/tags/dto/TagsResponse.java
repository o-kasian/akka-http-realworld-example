package realworld.com.tags.dto;

import java.util.List;

/**
 * DTO for the tags response, equivalent to the Scala ResponseTags case class
 */
public class TagsResponse {
    private List<String> tags;

    public TagsResponse() {
    }

    public TagsResponse(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}