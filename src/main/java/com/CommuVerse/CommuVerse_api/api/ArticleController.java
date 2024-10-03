package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.config.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO dto) {
        ArticleDTO createdArticle = articleService.createArticle(dto);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> searchArticles(@RequestParam String keyword) {
        List<ArticleDTO> articles = articleService.searchArticlesByKeyword(keyword);

        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    @PutMapping("/{articleId}/edit")
    public ResponseEntity<ArticleDTO> editArticle(
            @PathVariable Integer articleId,
            @RequestBody ArticleDTO dto) {
        ArticleDTO updatedArticle = articleService.editArticle(articleId, dto);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }
    @GetMapping("/filter/type")
    public ResponseEntity<List<ArticleDTO>> filterByType(@RequestParam String type) {
        List<ArticleDTO> articles = articleService.filterArticlesByType(type);

        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/filter/publication-date")
public ResponseEntity<List<ArticleDTO>> filterByPublicationDate(@RequestParam String date) {
    LocalDate publicationDate = LocalDate.parse(date);
    List<ArticleDTO> articles = articleService.filterArticlesByPublicationDate(publicationDate);

    if (articles.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(articles, HttpStatus.OK);
}


    @GetMapping("/{articleId}/showArticle")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Integer articleId){
        ArticleDTO article = articleService.getArticle(articleId);
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(article, HttpStatus.OK);
        }

    @DeleteMapping("/{articleId}/deleteArticle")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Integer articleId) {
        boolean isDeleted = articleService.deleteArticle(articleId);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{articleId}/updateArticle")
    public ResponseEntity<ArticleDTO> updateArticleById(@PathVariable Integer articleId, @RequestBody ArticleDTO articleDTO) {

        ArticleDTO updatedArticle = articleService.updateArticle(articleId, articleDTO);

        if (updatedArticle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }


    @PutMapping("/{articleId}/assignTags")
    public ResponseEntity<ArticleDTO> assignTagsToArticle(
            @PathVariable Integer articleId,
            @RequestBody List<String> tagNames) {

        ArticleDTO updatedArticle = articleService.assignTagsToArticle(articleId, tagNames);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }
}