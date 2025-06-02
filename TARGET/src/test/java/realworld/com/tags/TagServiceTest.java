package realworld.com.tags;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import realworld.com.articles.TagV;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TagServiceTest {

    @Mock
    private TagStorage tagStorage;

    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagService = new TagService(tagStorage);
    }

    @Test
    void getTags_shouldReturnTags() {
        // Arrange
        List<TagV> expectedTags = Arrays.asList(
                new TagV(1L, "one"),
                new TagV(2L, "two")
        );
        when(tagStorage.getTags()).thenReturn(CompletableFuture.completedFuture(expectedTags));

        // Act
        CompletableFuture<ResponseTags> result = tagService.getTags();

        // Assert
        ResponseTags responseTags = result.join();
        assertEquals(expectedTags, responseTags.getTags());
    }
}