package realworld.com.test_helpers;

import realworld.com.core.User;

public class Authors {
    public static final User normalAuthor = new User(
            1L,
            "author",
            "password",
            "email",
            java.util.Optional.empty(),
            java.util.Optional.empty(),
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
    );
}