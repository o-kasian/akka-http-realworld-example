package realworld.com.articles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import realworld.com.BaseServiceTest;
import realworld.com.tags.TagStorage;
import realworld.com.tags.TagV;
import realworld.com.users.UserStorage;
import realworld.com.users.Author;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ArticleServiceTest extends BaseServiceTest {

    @Mock
    private ArticleStorage articleStorage;
    @Mock
    private UserStorage userStorage;
    @Mock
    private TagStorage tagStorage;

    private ArticleService articleService;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleStorage, userStorage, tagStorage);
    }

    @Test
    void shouldReturnArticlesByUsername() {
        // Given
        Article article = new Article(0L, "slug", "title", "description", "body", 1L, 
            currentWhenInserting(), currentWhenInserting());
        ArticleRequest request = new ArticleRequest(null, Optional.of("testAuthor"), 
            Optional.empty(), Optional.empty(), Optional.empty());
        
        when(articleStorage.getArticles(request)).thenReturn(List.of(article));

        // When
        List<Article> result = articleService.getArticles(request);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(article);
    }

    @Test
    void shouldCreateArticle() {
        // Given
        ArticlePosted newArticle = new ArticlePosted("title", "description", "body", Collections.emptyList());
        Article createdArticle = new Article(1L, "title", "description", "body", 1L,
            currentWhenInserting(), currentWhenInserting());
        
        when(articleStorage.createArticle(any())).thenReturn(createdArticle);

        // When
        Optional<ArticlePosted> result = articleService.createArticle(0L, newArticle, Optional.of(1L));

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(newArticle);
    }

    @Test
    void shouldGetFeeds() {
        // Given
        List<Article> articles = Arrays.asList(
            new Article(0L, "slug", "title", "description", "body", 1L,
                currentWhenInserting(), currentWhenInserting()),
            new Article(1L, "slug-2", "title-2", "description-2", "body-2", 2L,
                currentWhenInserting(), currentWhenInserting())
        );

        when(articleStorage.getArticlesByFollowees(eq(1L), eq(Optional.empty()), eq(Optional.empty())))
            .thenReturn(articles);
        when(articleStorage.isFavoriteArticleIds(any(), any())).thenReturn(Arrays.asList(1L, 2L));
        when(articleStorage.countFavorites(any())).thenReturn(Collections.singletonList(new FavoriteCount(1L, 0)));
        when(userStorage.getUsersByUserIds(any())).thenReturn(Collections.singletonList(createNormalAuthor()));
        when(tagStorage.getTagsByArticles(any())).thenReturn(Arrays.asList(
            new ArticleTag(0L, new TagV(1L, "tag first")),
            new ArticleTag(1L, new TagV(1L, "tag second"))
        ));

        // When
        ArticleFeed result = articleService.getFeeds(1L, Optional.empty(), Optional.empty());

        // Then
        assertThat(result.getArticlesCount()).isEqualTo(2);
        assertThat(result.getArticles().get(0).getTitle()).isEqualTo("title");
        assertThat(result.getArticles().get(0).isFavorited()).isFalse();
        assertThat(result.getArticles().get(0).getTagList()).containsExactly("tag first");
        assertThat(result.getArticles().get(1).getTagList()).containsExactly("tag second");
    }

    @Test
    void shouldGetArticleBySlug() {
        // Given
        Article article = new Article(0L, "sample-slug", "title", "description", "body", 1L,
            currentWhenInserting(), currentWhenInserting());
        when(articleStorage.getArticleBySlug("sample-slug")).thenReturn(Optional.of(article));

        // When
        Optional<Article> result = articleService.getArticleBySlug("sample-slug", 1L);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(article);
    }

    @Test
    void shouldUpdateArticle() {
        // Given
        String updateTitle = "title-test";
        String sampleSlug = "sample-slug";
        ArticleUpdated articleUpdated = new ArticleUpdated(Optional.of(updateTitle), 
            Optional.empty(), Optional.empty());
        
        Article originalArticle = new Article(0L, sampleSlug, "old-title", "description", 
            "body", 1L, currentWhenInserting(), currentWhenInserting());
        Article updatedArticle = new Article(0L, sampleSlug, updateTitle, "description", 
            "body", 1L, currentWhenInserting(), currentWhenInserting());

        when(articleStorage.getArticleBySlug(sampleSlug)).thenReturn(Optional.of(originalArticle));
        when(articleStorage.updateArticle(any())).thenReturn(updatedArticle);
        when(articleStorage.favoriteArticle(any(), any())).thenReturn(new Favorite(0L, 1L, 1L));
        when(articleStorage.countFavorite(any())).thenReturn(1);
        when(userStorage.getUser(any())).thenReturn(Optional.of(createNormalAuthor()));
        when(tagStorage.getTagsByArticle(any())).thenReturn(Collections.singletonList(new TagV(1L, "first")));

        // When
        Optional<ArticleResponse> result = articleService.updateArticleBySlug(sampleSlug, 1L, articleUpdated);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getArticle().getTitle()).isEqualTo(updateTitle);
        assertThat(result.get().getArticle().getSlug()).isEqualTo(sampleSlug);
        assertThat(result.get().getArticle().getTagList()).containsExactly("first");
    }

    private Author createNormalAuthor() {
        return new Author(1L, "normal-author", "bio", "image", "email@test.com", "password");
    }
}