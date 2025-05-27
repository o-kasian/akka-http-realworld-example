package com.realworld.tags;

import com.realworld.articles.TagV;
import java.util.List;

public class TagsResponse {
    private List<TagV> tags;

    public TagsResponse() {
    }

    public TagsResponse(List<TagV> tags) {
        this.tags = tags;
    }

    public List<TagV> getTags() {
        return tags;
    }

    public void setTags(List<TagV> tags) {
        this.tags = tags;
    }
}