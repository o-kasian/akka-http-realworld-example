package com.realworld.articles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles_tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTagTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "tag_id")
    private Long tagId;
}