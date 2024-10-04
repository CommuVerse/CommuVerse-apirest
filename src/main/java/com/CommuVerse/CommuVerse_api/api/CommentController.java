package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.CommentDTO;
import com.CommuVerse.CommuVerse_api.service.CommentService;
import com.CommuVerse.CommuVerse_api.config.JwtUtil; // Importar JwtUtil
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil; // Inyección de JwtUtil

    // Agregar un comentario (requiere autenticación)
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(
            @RequestHeader("Authorization") String authHeader, // Encabezado de autorización
            @RequestBody CommentDTO dto) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
        String nickname;
        try {
            nickname = jwtUtil.extractUsername(token); // Extraer nickname del token
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Token expirado
        }

        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Token no válido
        }

        CommentDTO createdComment = commentService.addComment(dto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Mostrar todos los comentarios de un artículo
    @GetMapping("/article/{articleId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticle(@PathVariable Integer articleId) {
        List<CommentDTO> comments = commentService.getCommentsByArticle(articleId);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Editar un comentario (requiere autenticación)
    @PutMapping("/{commentId}/edit")
    public ResponseEntity<CommentDTO> editComment(
            @RequestHeader("Authorization") String authHeader, // Encabezado de autorización
            @PathVariable Integer commentId,
            @RequestBody CommentDTO dto) {
        
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
        String nickname = jwtUtil.extractUsername(token); // Extraer nickname del token

        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        CommentDTO updatedComment = commentService.editComment(commentId, dto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Eliminar un comentario (requiere autenticación)
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @RequestHeader("Authorization") String authHeader, // Encabezado de autorización
            @PathVariable Integer commentId) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
        String nickname = jwtUtil.extractUsername(token); // Extraer nickname del token

        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
