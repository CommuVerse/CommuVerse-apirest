package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

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


}