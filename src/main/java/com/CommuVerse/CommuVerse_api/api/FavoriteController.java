package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.FavoriteDTO;
import com.CommuVerse.CommuVerse_api.service.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // POST /favorites/addFavorite
    @PostMapping("/addFavorite")
    public ResponseEntity<FavoriteDTO> addFavorite(@RequestBody FavoriteDTO favoriteDTO) {
        try {
            FavoriteDTO createdFavorite = favoriteService.addFavorite(favoriteDTO.getReader().getId(), favoriteDTO.getArticle().getId());
            return ResponseEntity.ok(createdFavorite);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @DeleteMapping("/deleteFavorite/{id}")
public ResponseEntity<String> deleteFavorite(@PathVariable("id") Integer id) {
    try {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.ok("Artículo eliminado de favoritos con éxito");
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Artículo no encontrado en favoritos");
    }
}

}