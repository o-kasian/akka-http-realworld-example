package realworld.com.tags;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import realworld.com.articles.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class JdbcTagStorageTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ArticleTagRepository articleTagRepository;

    private JdbcTagStorage tagStorage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagStorage = new JdbcTagStorage(tagRepository, articleTagRepository);
    }

    @Test
    void getTags_shouldReturnAllTags() {
        // Arrange
        List<TagEntity> tagEntities = Arrays.asList(
                new TagEntity(1L, "one"),
                new TagEntity(2L, "two")
        );
        when(tagRepository.findAll()).thenReturn(tagEntities);

        // Act
        CompletableFuture<List<TagV>> result = tagStorage.getTags();

        // Assert
        List<TagV> tags = result.join();
        assertEquals(2, tags.size());
        assertEquals("one", tags.get(0).getName());
        assertEquals("two", tags.get(1).getName());
    }

    @Test
    void findTagByNames_shouldReturnMatchingTags() {
        // Arrange
        List<String> tagNames = Arrays.asList("test");
        List<TagEntity> tagEntities = Arrays.asList(
                new TagEntity(1L, "test")
        );
        when(tagRepository.findByNameIn(tagNames)).thenReturn(tagEntities);

        // Act
        CompletableFuture<List<TagV>> result = tagStorage.findTagByNames(tagNames);

        // Assert
        List<TagV> tags = result.join();
        assertEquals(1, tags.size());
        assertEquals("test", tags.get(0).getName());
    }

    @Test
    void insertAndGet_shouldReturnInsertedTags() {
        // Arrange
        List<TagV> tagsToInsert = Arrays.asList(
                new TagV(-1L, "test")
        );
        List<TagEntity> savedEntities = Arrays.asList(
                new TagEntity(1L, "test")
        );
        when(tagRepository.saveAll(any())).thenReturn(savedEntities);

        // Act
        CompletableFuture<List<TagV>> result = tagStorage.insertAndGet(tagsToInsert);

        // Assert
        List<TagV> tags = result.join();
        assertEquals(1, tags.size());
        assertEquals(1L, tags.get(0).getId());
        assertEquals("test", tags.get(0).getName());
    }
}