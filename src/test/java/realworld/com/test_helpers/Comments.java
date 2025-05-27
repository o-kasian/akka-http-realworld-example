package realworld.com.test_helpers;

import realworld.com.articles.comments.Comment;
import java.util.List;
import java.util.Arrays;

public class Comments {
    public static final Comment normalComment = new Comment(
            1L,
            "first comment",
            1L,
            1L,
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
    );

    public static final List<Comment> comments = Arrays.asList(
        new Comment(
            1L,
            "first comment",
            2L,
            3L,
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
        ),
        new Comment(
            2L,
            "second comment",
            2L,
            4L,
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
        ),
        new Comment(
            3L,
            "third comment",
            2L,
            5L,
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
        )
    );
}