package realworld.com.model.dto;

import realworld.com.model.Profile;

import java.util.List;

/**
 * Response wrapper classes for API responses
 */
public class ResponseWrappers {

    // User response wrapper
    public static class ResponseUser {
        private UserWithToken user;

        public ResponseUser() {
        }

        public ResponseUser(UserWithToken user) {
            this.user = user;
        }

        public UserWithToken getUser() {
            return user;
        }

        public void setUser(UserWithToken user) {
            this.user = user;
        }
    }

    // Profile response wrapper
    public static class ResponseProfile {
        private Profile profile;

        public ResponseProfile() {
        }

        public ResponseProfile(Profile profile) {
            this.profile = profile;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }
    }

    // Article response wrapper
    public static class ForResponseArticle {
        private ArticleForResponse article;

        public ForResponseArticle() {
        }

        public ForResponseArticle(ArticleForResponse article) {
            this.article = article;
        }

        public ArticleForResponse getArticle() {
            return article;
        }

        public void setArticle(ArticleForResponse article) {
            this.article = article;
        }
    }

    // Articles list response wrapper
    public static class ForResponseArticles {
        private List<ArticleForResponse> articles;
        private int articlesCount;

        public ForResponseArticles() {
        }

        public ForResponseArticles(List<ArticleForResponse> articles, int articlesCount) {
            this.articles = articles;
            this.articlesCount = articlesCount;
        }

        public List<ArticleForResponse> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticleForResponse> articles) {
            this.articles = articles;
        }

        public int getArticlesCount() {
            return articlesCount;
        }

        public void setArticlesCount(int articlesCount) {
            this.articlesCount = articlesCount;
        }
    }

    // Comment response wrapper
    public static class CommentResponse {
        private CommentData comment;

        public CommentResponse() {
        }

        public CommentResponse(CommentData comment) {
            this.comment = comment;
        }

        public CommentData getComment() {
            return comment;
        }

        public void setComment(CommentData comment) {
            this.comment = comment;
        }
    }

    // Comments list response wrapper
    public static class CommentsResponse {
        private List<CommentData> comments;

        public CommentsResponse() {
        }

        public CommentsResponse(List<CommentData> comments) {
            this.comments = comments;
        }

        public List<CommentData> getComments() {
            return comments;
        }

        public void setComments(List<CommentData> comments) {
            this.comments = comments;
        }
    }
}