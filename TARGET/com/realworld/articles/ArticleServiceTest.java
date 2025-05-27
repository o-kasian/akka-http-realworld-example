package com.realworld.articles;

import com.realworld.tags.TagStorage;
import com.realworld.tags.TagV;
import com.realworld.users.UserStorage;
import com.realworld.utils.StorageRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ArticleServiceTest {

    @Mock
    private ArticleStorage articleStorage;
    
    @Mock
    private UserStorage userStorage;
    
    @Mock
    private TagStorage tagStorage;
    
    @Mock
    private StorageRunner storageRunner;
    
    private ArticleService articleService;
    
    private final LocalDateTime currentWhenInserting = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(storageRunner, articleStorage, userStorage, tagStorage);
    }

    @Nested
    @DisplayName("getArticles")
    class GetArticlesTests {
        @Test
        void shouldReturnArticlesByUsername() {
            // Given
            Article article1 = new Article(0L, "slug", "title", "description", "body", 1L,
                    currentWhenInserting, currentWhenInserting);
            ArticleRequest request = ArticleRequest.builder()
                    .authorName(Optional.of("testAuthor"))
                    .build();
            
            when(articleStorage.getArticles(request)).thenReturn(Mono.just(List.of(article1)));

            // When & Then
            StepVerifier.create(articleService.getArticles(request))
                    .assertNext(articles -> assertThat(articles).containsExactly(article1))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("createArticle")
    class CreateArticleTests {
        @Test
        void shouldCreateAndReturnArticle() {
            // Given
            ArticlePosted newPostArticle = new ArticlePosted("title", "description", "body", Collections.emptyList());
            when(articleStorage.createArticle(any())).thenReturn(Mono.just(Articles.normalArticle()));

            // When & Then
            StepVerifier.create(articleService.createArticle(0L, newPostArticle, Optional.of(1L)))
                    .assertNext(article -> assertThat(article).isEqualTo(Optional.of(newPostArticle)))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("getFeeds")
    class GetFeedsTests {
        @Test
        void shouldReturnFeeds() {
            // Given
            List<Article> articles = Arrays.asList(
                new Article(0L, "slug", "title", "description", "body", 1L,
                        currentWhenInserting, currentWhenInserting),
                new Article(1L, "slug-2", "title-2", "description-2", "body-2", 2L,
                        currentWhenInserting, currentWhenInserting)
            );

            when(articleStorage.getArticlesByFollowees(eq(1L), eq(Optional.empty()), eq(Optional.empty())))
                    .thenReturn(Mono.just(articles));
            when(articleStorage.isFavoriteArticleIds(any(), any()))
                    .thenReturn(Mono.just(Arrays.asList(1L, 2L)));
            when(articleStorage.countFavorites(any()))
                    .thenReturn(Mono.just(Collections.singletonList(new Tuple2<>(1L, 0L))));
            when(userStorage.getUsersByUserIds(any()))
                    .thenReturn(Mono.just(Collections.singletonList(Authors.normalAuthor())));
            when(tagStorage.getTagsByArticles(any()))
                    .thenReturn(Mono.just(Arrays.asList(
                            new Tuple2<>(0L, new TagV(1L, "tag first")),
                            new Tuple2<>(1L, new TagV(1L, "tag second"))
                    )));

            // When & Then
            StepVerifier.create(articleService.getFeeds(1L, Optional.empty(), Optional.empty()))
                    .assertNext(articleResponse -> {
                        assertThat(articleResponse.getArticlesCount()).isEqualTo(2);
                        assertThat(articleResponse.getArticles().get(0).getTitle()).isEqualTo("title");
                        assertThat(articleResponse.getArticles().get(0).isFavorited()).isFalse();
                        assertThat(articleResponse.getArticles().get(0).getTagList()).containsExactly("tag first");
                        assertThat(articleResponse.getArticles().get(1).getTagList()).containsExactly("tag second");
                    })
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("getArticleBySlug")
    class GetArticleBySlugTests {
        @Test
        void shouldReturnArticleBySlug() {
            // Given
            when(articleStorage.getArticleBySlug("sample-slug"))
                    .thenReturn(Mono.just(Optional.of(Articles.normalArticle())));

            // When & Then
            StepVerifier.create(articleService.getArticleBySlug("sample-slug", 1L))
                    .assertNext(article -> assertThat(article).isEqualTo(Optional.of(Articles.normalArticle())))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("updateArticleBySlug")
    class UpdateArticleBySlugTests {
        @Test
        void shouldUpdateArticle() {
            // Given
            String updateTitle = "title-test";
            String sampleSlug = "sample-slug";
            ArticleUpdated articleUpdated = new ArticleUpdated(Optional.of(updateTitle), Optional.empty(), Optional.empty());

            Article updatedArticle = Articles.normalArticle().toBuilder()
                    .title(updateTitle)
                    .build();

            when(articleStorage.getArticleBySlug(sampleSlug))
                    .thenReturn(Mono.just(Optional.of(Articles.normalArticle())));
            when(articleStorage.updateArticle(any()))
                    .thenReturn(Mono.just(updatedArticle));
            when(articleStorage.favoriteArticle(any(), any()))
                    .thenReturn(Mono.just(new Favorite(0L, 1L, 1L)));
            when(articleStorage.countFavorite(any()))
                    .thenReturn(Mono.just(1L));
            when(userStorage.getUser(any()))
                    .thenReturn(Mono.just(Optional.of(Authors.normalAuthor())));
            when(tagStorage.getTagsByArticle(any()))
                    .thenReturn(Mono.just(Collections.singletonList(new TagV(1L, "first"))));

            // When & Then
            StepVerifier.create(articleService.updateArticleBySlug(sampleSlug, 1L, articleUpdated))
                    .assertNext(articleOptional -> {
                        assertThat(articleOptional).isPresent();
                        ArticleResponse response = articleOptional.get();
                        assertThat(response.getArticle().getTitle()).isEqualTo(updateTitle);
                        assertThat(response.getArticle().getSlug()).isEqualTo(Articles.normalArticle().getSlug());
                        assertThat(response.getArticle().getDescription()).isEqualTo(Articles.normalArticle().getDescription());
                        assertThat(response.getArticle().getBody()).isEqualTo(Articles.normalArticle().getBody());
                        assertThat(response.getArticle().getTagList()).containsExactly("first");
                    })
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("deleteArticleBySlug")
    class DeleteArticleBySlugTests {
        @Test
        void shouldDeleteArticle() {
            // Given
            String slug = "dragon-dragon";
            when(articleStorage.deleteArticleBySlug(slug))
                    .thenReturn(Mono.empty());

            // When & Then
            StepVerifier.create(articleService.deleteArticleBySlug(slug))
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("favoriteArticle")
    class FavoriteArticleTests {
        @Test
        void shouldFavoriteArticle() {
            // Given
            String slug = "dragon-dragon";
            when(articleStorage.getArticleBySlug(slug))
                    .thenReturn(Mono.just(Optional.of(Articles.normalArticle())));
            when(articleStorage.countFavorite(any()))
                    .thenReturn(Mono.just(1L));
            when(articleStorage.favoriteArticle(any(), any()))
                    .thenReturn(Mono.just(new Favorite(0L, 0L, 0L)));
            when(userStorage.getUser(any()))
                    .thenReturn(Mono.just(Optional.of(Authors.normalAuthor())));
            when(tagStorage.getTagsByArticle(any()))
                    .thenReturn(Mono.just(Collections.singletonList(new TagV(1L, "first"))));

            // When & Then
            StepVerifier.create(articleService.favoriteArticle(0L, slug))
                    .assertNext(articleOptional -> {
                        assertThat(articleOptional).isPresent();
                        ArticleResponse response = articleOptional.get();
                        assertThat(response.getArticle().getTitle()).isEqualTo(Articles.normalArticle().getTitle());
                        assertThat(response.getArticle().getSlug()).isEqualTo(Articles.normalArticle().getSlug());
                        assertThat(response.getArticle().getDescription()).isEqualTo(Articles.normalArticle().getDescription());
                        assertThat(response.getArticle().getBody()).isEqualTo(Articles.normalArticle().getBody());
                        assertThat(response.getArticle().isFavorited()).isTrue();
                        assertThat(response.getArticle().getFavoritesCount()).isEqualTo(2);
                        assertThat(response.getArticle().getTagList()).containsExactly("first");
                    })
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("unfavoriteArticle")
    class UnfavoriteArticleTests {
        @Test
        void shouldUnfavoriteArticle() {
            // Given
            String slug = "dragon-dragon";
            when(articleStorage.getArticleBySlug(slug))
                    .thenReturn(Mono.just(Optional.of(Articles.normalArticle())));
            when(articleStorage.countFavorite(any()))
                    .thenReturn(Mono.just(0L));
            when(articleStorage.unFavoriteArticle(any(), any()))
                    .thenReturn(Mono.just(1));
            when(userStorage.getUser(any()))
                    .thenReturn(Mono.just(Optional.of(Authors.normalAuthor())));
            when(tagStorage.getTagsByArticle(any()))
                    .thenReturn(Mono.just(Collections.singletonList(new TagV(1L, "first"))));

            // When & Then
            StepVerifier.create(articleService.unFavoriteArticle(0L, slug))
                    .assertNext(articleOptional -> {
                        assertThat(articleOptional).isPresent();
                        ArticleResponse response = articleOptional.get();
                        assertThat(response.getArticle().getTitle()).isEqualTo(Articles.normalArticle().getTitle());
                        assertThat(response.getArticle().getSlug()).isEqualTo(Articles.normalArticle().getSlug());
                        assertThat(response.getArticle().getDescription()).isEqualTo(Articles.normalArticle().getDescription());
                        assertThat(response.getArticle().getBody()).isEqualTo(Articles.normalArticle().getBody());
                        assertThat(response.getArticle().isFavorited()).isFalse();
                        assertThat(response.getArticle().getFavoritesCount()).isEqualTo(0);
                        assertThat(response.getArticle().getTagList()).containsExactly("first");
                    })
                    .verifyComplete();
        }
    }
}