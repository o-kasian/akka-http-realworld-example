package realworld.com.model.dto;

import jakarta.validation.constraints.NotBlank;
import realworld.com.model.Article;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for creating a new article
 */
public class ArticlePosted {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Body cannot be empty")
    private String body;

    private List<String> tagList = new ArrayList<>();

    // Default constructor
    public ArticlePosted() {
    }

    public ArticlePosted(String title, String description, String body, List<String> tagList) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = tagList;
    }

    // Factory method to create an Article entity
    public Article toArticle(Long authorId) {
        Article article = new Article();
        article.setSlug(Article.slugify(title));
        article.setTitle(title);
        article.setDescription(description);
        article.setBody(body);
        article.setAuthorId(authorId);
        article.setCreatedAt(Instant.now());
        article.setUpdatedAt(Instant.now());
        return article;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }
}