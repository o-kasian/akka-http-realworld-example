package com.realworld.tags;

import com.realworld.articles.ArticleTag;
import com.realworld.articles.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagStorage extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t")
    List<Tag> getTags();

    @Query("SELECT NEW map(at.article.id as articleId, t as tag) FROM ArticleTag at JOIN at.tag t WHERE at.article.id IN :articleIds")
    List<Object[]> getTagsByArticles(@Param("articleIds") List<Long> articleIds);

    @Query("SELECT t FROM Tag t JOIN ArticleTag at ON at.tag.id = t.id WHERE at.article.id = :articleId")
    List<Tag> getTagsByArticle(@Param("articleId") Long articleId);

    @Query("SELECT t FROM Tag t WHERE t.name IN :tagNames")
    List<Tag> findTagByNames(@Param("tagNames") List<String> tagNames);

    @Query("SELECT t FROM Tag t WHERE t.id IN (SELECT t2.id FROM Tag t2 WHERE t2 IN :tags)")
    List<Tag> insertAndGet(@Param("tags") List<Tag> tags);
}