package realworld.com.articles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import realworld.com.BaseServiceTest;
import realworld.com.tags.JdbcTagStorage;
import realworld.com.tags.TagStorage;
import realworld.com.tags.TagV;
import realworld.com.users.JdbcUserStorage;
import realworld.com.users.User;
import realworld.com.users.UserStorage;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JdbcArticleStorage.class, JdbcUserStorage.class, JdbcTagStorage.class})
class ArticleStorageTest extends BaseServiceTest {

    @Autowired
    private ArticleStorage articleStorage;

    @Autowired
    private UserStorage userStorage;

    @Autowired
    private TagStorage tagStorage;

    private Article testArticle1;
    private User author;
    private User someone;

    @BeforeEach
    void setUp() {
        testArticle1 = new Article(0L, "title-one", "tile-one", "test description", "test body", 1L,
            currentWhenInserting(), currentWhenInserting());

        author = new User(1L, "author", "test", "test", null, null,
            currentWhenInserting(), currentWhenInserting());

        someone = new User(2L, "someone", "test-someone", "test-someone", null, null,
            currentWhenInserting(), currentWhenInserting());
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldReturnArticleByAuthorId() {
        User savedUser = userStorage.saveUser(author);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());

        List<Article> articles = articleStorage.getArticles(ArticleRequest.builder()
            .authorName(Optional.of(savedUser.getUsername()))
            .tag(Optional.empty())
            .favorited(Optional.empty())
            .limit(Optional.of(10))
            .offset(Optional.of(0))
            .build());

        assertThat(articles).hasSize(1);
        assertThat(articles.get(0)).isEqualToIgnoringGivenFields(
            testArticle1.toBuilder().id(articles.get(0).getId()).authorId(savedUser.getId()).build(),
            "createdAt", "updatedAt");
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldReturnArticleBySlug() {
        User savedUser = userStorage.saveUser(author);
        articleStorage.createArticle(testArticle1.toBuilder().authorId(savedUser.getId()).build());

        Optional<Article> article = articleStorage.getArticleBySlug("title-one");

        assertThat(article).isPresent();
        assertThat(article.get()).isEqualToIgnoringGivenFields(
            testArticle1.toBuilder().id(article.get().getId()).authorId(savedUser.getId()).build(),
            "createdAt", "updatedAt");
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldUpdateExistingArticle() {
        String updatedBody = "updated body";
        User savedUser = userStorage.saveUser(author);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());

        Article updatedArticle = articleStorage.updateArticle(
            savedArticle.toBuilder().body(updatedBody).build());
        Optional<Article> retrievedArticle = articleStorage.getArticleBySlug("title-one");

        assertThat(retrievedArticle).isPresent();
        assertThat(retrievedArticle.get().getBody()).isEqualTo(updatedBody);
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldDeleteArticleBySlug() {
        User savedUser = userStorage.saveUser(author);
        articleStorage.createArticle(testArticle1.toBuilder().authorId(savedUser.getId()).build());

        articleStorage.deleteArticleBySlug(testArticle1.getSlug());
        Optional<Article> article = articleStorage.getArticleBySlug(testArticle1.getSlug());

        assertThat(article).isEmpty();
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldSetAndCountFavorite() {
        User savedUser = userStorage.saveUser(author);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());

        Favorite favorite = articleStorage.favoriteArticle(savedUser.getId(), savedArticle.getId());
        List<FavoriteCount> favoriteCounts = articleStorage.countFavorites(
            Arrays.asList(savedArticle.getId()));

        assertThat(favoriteCounts).hasSize(1);
        assertThat(favoriteCounts.get(0).getCount()).isEqualTo(1);
        assertThat(favoriteCounts.get(0).getArticleId()).isEqualTo(savedArticle.getId());
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldUnfavoriteArticle() {
        User savedUser = userStorage.saveUser(author);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());

        articleStorage.favoriteArticle(savedUser.getId(), savedArticle.getId());
        articleStorage.unFavoriteArticle(savedUser.getId(), savedArticle.getId());
        List<FavoriteCount> favoriteCounts = articleStorage.countFavorites(
            Arrays.asList(savedArticle.getId()));

        assertThat(favoriteCounts).isEmpty();
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldCountMultipleFavorites() {
        User savedUser = userStorage.saveUser(author);
        User savedSomeone = userStorage.saveUser(someone);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());

        articleStorage.favoriteArticle(savedUser.getId(), savedArticle.getId());
        articleStorage.favoriteArticle(savedSomeone.getId(), savedArticle.getId());
        int favoriteCount = articleStorage.countFavorite(savedArticle.getId());

        assertThat(favoriteCount).isEqualTo(2);
    }

    @Test
    @Sql("/cleanup.sql")
    void shouldInsertArticleTag() {
        User savedUser = userStorage.saveUser(author);
        Article savedArticle = articleStorage.createArticle(
            testArticle1.toBuilder().authorId(savedUser.getId()).build());
        List<TagV> tags = tagStorage.insertAndGet(Arrays.asList(new TagV(0L, "test")));

        List<ArticleTag> articleTags = articleStorage.insertArticleTag(Arrays.asList(
            new ArticleTag(0L, savedArticle.getId(), tags.get(0).getId())));

        assertThat(articleTags).hasSize(1);
        assertThat(articleTags.get(0).getArticleId()).isEqualTo(savedArticle.getId());
        assertThat(articleTags.get(0).getTagId()).isEqualTo(tags.get(0).getId());
    }
}