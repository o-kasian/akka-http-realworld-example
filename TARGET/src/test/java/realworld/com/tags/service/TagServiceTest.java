package realworld.com.tags.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realworld.com.tags.dto.TagsResponse;
import realworld.com.tags.model.Tag;
import realworld.com.tags.repository.TagRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    public void testGetAllTags() {
        // Arrange
        List<Tag> tags = Arrays.asList(
                new Tag(1L, "java"),
                new Tag(2L, "spring"),
                new Tag(3L, "test")
        );
        when(tagRepository.findAll()).thenReturn(tags);

        // Act
        TagsResponse response = tagService.getAllTags();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getTags());
        assertEquals(3, response.getTags().size());
        assertEquals("java", response.getTags().get(0));
        assertEquals("spring", response.getTags().get(1));
        assertEquals("test", response.getTags().get(2));
    }
}