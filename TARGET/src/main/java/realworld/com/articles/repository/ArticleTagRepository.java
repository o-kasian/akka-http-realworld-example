package realworld.com.articles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import realworld.com.articles.model.ArticleTag;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
}