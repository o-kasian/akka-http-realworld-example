package realworld.com.model.dto;

import realworld.com.model.Article;

import java.time.Instant;

/**
 * DTO for updating an article
 */
public class ArticleUpdated {
    private String title;
    private String description;
    private String body;

    // Default constructor
    public ArticleUpdated() {
    }

    public ArticleUpdated(String title, String description, String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }

    // Method to apply updates to an existing article
    public Article applyTo(Article article) {
        if (title != null) {
            article.setTitle(title);
            article.setSlug(Article.slugify(title));
        }
        if (description != null) {
            article.setDescription(description);
        }
        if (body != null) {
            article.setBody(body);
        }
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
}