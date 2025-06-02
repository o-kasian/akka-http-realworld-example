package realworld.com.tags;

import realworld.com.articles.TagV;

import java.util.List;
import java.util.Objects;

/**
 * Response object for tags endpoint.
 */
public class ResponseTags {
    private List<TagV> tags;

    /**
     * Default constructor required for serialization
     */
    public ResponseTags() {
    }

    /**
     * Constructor with tags list
     * 
     * @param tags list of tags
     */
    public ResponseTags(List<TagV> tags) {
        this.tags = tags;
    }

    /**
     * Gets the list of tags
     * 
     * @return list of tags
     */
    public List<TagV> getTags() {
        return tags;
    }

    /**
     * Sets the list of tags
     * 
     * @param tags list of tags
     */
    public void setTags(List<TagV> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTags that = (ResponseTags) o;
        return Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags);
    }

    @Override
    public String toString() {
        return "ResponseTags{" +
                "tags=" + tags +
                '}';
    }
}