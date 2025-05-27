package com.realworld.test_helpers;

import com.realworld.articles.comments.Comment;
import java.util.Arrays;
import java.util.List;

public class Comments {
    public static final Comment normalComment = new Comment(
            1L,
            "first comment",
            1L,
            1L,
            Dates.getCurrentWhenInserting(),
            Dates.getCurrentWhenInserting()
    );

    public static final List<Comment> comments = Arrays.asList(
            new Comment(
                    1L,
                    "first comment",
                    2L,
                    3L,
                    Dates.getCurrentWhenInserting(),
                    Dates.getCurrentWhenInserting()
            ),
            new Comment(
                    2L,
                    "second comment",
                    2L,
                    4L,
                    Dates.getCurrentWhenInserting(),
                    Dates.getCurrentWhenInserting()
            ),
            new Comment(
                    3L,
                    "third comment",
                    2L,
                    5L,
                    Dates.getCurrentWhenInserting(),
                    Dates.getCurrentWhenInserting()
            )
    );
}