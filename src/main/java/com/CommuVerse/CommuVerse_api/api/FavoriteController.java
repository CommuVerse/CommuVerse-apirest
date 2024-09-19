package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.FavoriteDTO;
import com.CommuVerse.CommuVerse_api.service.impl.FavoriteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // GET /favorites/reader/{readerId}
    @GetMapping("/reader/{readerId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByReaderId(@PathVariable("readerId") Integer readerId) {
        List<FavoriteDTO> favorites = favoriteService.getFavoritesByReaderId(readerId);
        return ResponseEntity.ok(favorites);
    }

    // DELETE /favorites/{id}
    @DeleteMapping("/deleteFavorite{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable("id") Integer id) {
        try {
            favoriteService.deleteFavorite(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}