package com.realworld.tags;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TagStorageTest {

    @Autowired
    private TagStorage tagStorage;

    @BeforeEach
    void setUp() {
        // Database cleaning is handled by @Transactional
    }

    @Nested
    class FindTag {
        @Test
        void shouldReturnCorrectTag() {
            // Given
            List<TagV> tagsToInsert = Arrays.asList(
                TagV.create("test"),
                TagV.create("test2")
            );
            tagStorage.insertAndGet(tagsToInsert);

            // When
            List<TagV> foundTags = tagStorage.findTagByNames(Arrays.asList("test"));

            // Then
            assertThat(foundTags.get(0).getName()).isEqualTo("test");
        }
    }

    @Nested
    class InsertTag {
        @Test
        void shouldInsertAndReturnTags() {
            // Given
            List<TagV> tagsToInsert = Arrays.asList(TagV.create("test"));

            // When
            List<TagV> insertedTags = tagStorage.insertAndGet(tagsToInsert);

            // Then
            assertThat(insertedTags).hasSize(1);
            TagV insertedTag = insertedTags.get(0);
            assertThat(insertedTag.getName()).isEqualTo("test");
            assertThat(insertedTag.getId()).isNotNull();
        }
    }

    @Test
    void shouldReturnAllTags() {
        // Given
        List<TagV> tagsToInsert = Arrays.asList(
            new TagV(1L, "one"),
            new TagV(2L, "two")
        );
        tagStorage.insertAndGet(tagsToInsert);

        // When
        List<TagV> allTags = tagStorage.getTags();

        // Then
        assertThat(allTags).hasSize(tagsToInsert.size());
        assertThat(allTags).extracting(TagV::getName)
                          .containsExactlyInAnyOrder("one", "two");
    }
}