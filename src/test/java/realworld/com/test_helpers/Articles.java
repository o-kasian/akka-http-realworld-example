package realworld.com.test_helpers;

import realworld.com.articles.Article;

public class Articles {
    public static final Article normalArticle = new Article(
            1L,
            "slug",
            "title", 
            "description",
            "body",
            1L,
            Dates.currentWhenInserting(),
            Dates.currentWhenInserting()
    );
}