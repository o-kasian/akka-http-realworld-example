package realworld.com.tags;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import realworld.com.articles.TagV;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TagControllerTest {

    @Mock
    private TagService tagService;

    private TagController tagController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tagController = new TagController(tagService);
    }

    @Test
    void getTags_shouldReturnTagsResponse() {
        // Arrange
        List<TagV> tags = Arrays.asList(
                new TagV(1L, "one"),
                new TagV(2L, "two")
        );
        ResponseTags responseTags = new ResponseTags(tags);
        when(tagService.getTags()).thenReturn(CompletableFuture.completedFuture(responseTags));

        // Act
        CompletableFuture<ResponseEntity<ResponseTags>> result = tagController.getTags();

        // Assert
        ResponseEntity<ResponseTags> response = result.join();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseTags, response.getBody());
    }
}