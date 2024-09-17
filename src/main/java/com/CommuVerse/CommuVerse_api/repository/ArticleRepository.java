package com.CommuVerse.CommuVerse_api.repository;

import com.CommuVerse.CommuVerse_api.model.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    List<Article> findByCreatorId(Integer creatorId);
}
