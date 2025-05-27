package com.realworld.routes;

import com.realworld.articles.ArticleController;
import com.realworld.articles.comments.CommentController;
import com.realworld.profile.ProfileController;
import com.realworld.tags.TagController;
import com.realworld.users.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HttpRoute {

    private final UserController userController;
    private final ProfileController profileController;
    private final ArticleController articleController;
    private final CommentController commentController;
    private final TagController tagController;

    public HttpRoute(UserController userController,
                    ProfileController profileController,
                    ArticleController articleController,
                    CommentController commentController,
                    TagController tagController) {
        this.userController = userController;
        this.profileController = profileController;
        this.articleController = articleController;
        this.commentController = commentController;
        this.tagController = tagController;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}