package realworld.com.model.dto;

/**
 * DTO for article search/filtering requests
 */
public class ArticleRequest {
    private String tag;
    private String authorName;
    private String favorited;
    private Integer limit;
    private Integer offset;

    // Default constructor
    public ArticleRequest() {
    }

    public ArticleRequest(String tag, String authorName, String favorited, Integer limit, Integer offset) {
        this.tag = tag;
        this.authorName = authorName;
        this.favorited = favorited;
        this.limit = limit;
        this.offset = offset;
    }

    // Getters and Setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFavorited() {
        return favorited;
    }

    public void setFavorited(String favorited) {
        this.favorited = favorited;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}