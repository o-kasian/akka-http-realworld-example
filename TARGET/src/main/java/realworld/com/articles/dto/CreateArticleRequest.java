package realworld.com.articles.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {
    private ArticleCreateDto article;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArticleCreateDto {
        private String title;
        private String description;
        private String body;
        private List<String> tagList;
    }
}