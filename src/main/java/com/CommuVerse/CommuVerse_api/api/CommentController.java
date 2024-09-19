package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.CommentDTO;
import com.CommuVerse.CommuVerse_api.service.CommentService;
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

    // Agregar un comentario
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO dto) {
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

    // Editar un comentario (dentro de los 10 minutos)
    @PutMapping("/{commentId}/edit")
    public ResponseEntity<CommentDTO> editComment(@PathVariable Integer commentId, @RequestBody CommentDTO dto) {
        CommentDTO updatedComment = commentService.editComment(commentId, dto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // Eliminar un comentario (dentro de los 10 minutos)
    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}