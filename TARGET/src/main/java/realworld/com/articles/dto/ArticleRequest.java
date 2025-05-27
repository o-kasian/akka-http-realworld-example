package realworld.com.articles.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleRequest {
    private String tag;
    private String authorName;
    private String favorited;
    private Long limit;
    private Long offset;
}