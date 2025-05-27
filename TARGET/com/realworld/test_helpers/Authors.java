package com.realworld.test_helpers;

import com.realworld.core.User;

/**
 * Test helper class providing author data for tests.
 */
public final class Authors {
    private Authors() {
        // Utility class, prevent instantiation
    }

    public static final User NORMAL_AUTHOR = User.builder()
            .id(1L)
            .username("author")
            .password("password")
            .email("email")
            .bio(null)
            .image(null)
            .createdAt(Dates.getCurrentWhenInserting())
            .updatedAt(Dates.getCurrentWhenInserting())
            .build();
}