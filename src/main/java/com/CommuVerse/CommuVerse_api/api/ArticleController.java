package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.dto.TagDTO;
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

    // Crear un artículo
    @PostMapping("/create")
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO dto) {
        ArticleDTO createdArticle = articleService.createArticle(dto);
        return new ResponseEntity<>(createdArticle, HttpStatus.CREATED);
    }

    // Buscar artículos por palabra clave
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> searchArticles(@RequestParam String keyword) {
        List<ArticleDTO> articles = articleService.searchArticlesByKeyword(keyword);

        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Editar un artículo por su ID
    @PutMapping("/{articleId}/edit")
    public ResponseEntity<ArticleDTO> editArticle(@PathVariable Integer articleId, @RequestBody ArticleDTO dto) {
        ArticleDTO updatedArticle = articleService.editArticle(articleId, dto);
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

    // Filtrar artículos por tipo
    @GetMapping("/filter/type")
    public ResponseEntity<List<ArticleDTO>> filterByType(@RequestParam String type) {
        List<ArticleDTO> articles = articleService.filterArticlesByType(type);

        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Filtrar artículos por fecha de publicación
    @GetMapping("/filter/publication-date")
    public ResponseEntity<List<ArticleDTO>> filterByPublicationDate(@RequestParam String date) {
        LocalDate publicationDate = LocalDate.parse(date);  // Asegúrate de que el formato sea yyyy-MM-dd
        List<ArticleDTO> articles = articleService.filterArticlesByPublicationDate(publicationDate);

        if (articles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // Obtener un artículo por su ID
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Integer articleId) {
        ArticleDTO article = articleService.getArticle(articleId);

        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    // Eliminar un artículo por su ID
    @DeleteMapping("/{articleId}/deleteArticle")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Integer articleId) {
        boolean isDeleted = articleService.deleteArticle(articleId);

        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Asignar etiquetas a un artículo
    @PutMapping("/{articleId}/assignTags")
    public ResponseEntity<ArticleDTO> assignTagsToArticle(
            @PathVariable Integer articleId,
            @RequestBody TagDTO tagDTO) {
        ArticleDTO updatedArticle = articleService.assignTagsToArticle(articleId, tagDTO.getNombreEtiqueta(), tagDTO.getDescripcion());
        return new ResponseEntity<>(updatedArticle, HttpStatus.OK);
    }

}
