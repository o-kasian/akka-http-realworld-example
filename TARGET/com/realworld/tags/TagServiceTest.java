package com.realworld.tags;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.realworld.articles.TagV;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class TagServiceTest {

    @Mock
    private TagStorage tagStorage;
    
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagService = new TagService(tagStorage);
    }

    @Test
    void getTags_ShouldReturnTags() {
        // Given
        List<TagV> expectedTags = Arrays.asList(
            new TagV(1L, "one"),
            new TagV(1L, "two")
        );
        when(tagStorage.getTags()).thenReturn(expectedTags);

        // When
        ResponseTags result = tagService.getTags();

        // Then
        assertThat(result.getTags())
            .containsExactlyElementsOf(expectedTags);
    }
}