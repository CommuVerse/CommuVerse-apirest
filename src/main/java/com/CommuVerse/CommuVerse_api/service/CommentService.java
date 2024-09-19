package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.CommentDTO;
import com.CommuVerse.CommuVerse_api.mapper.CommentMapper;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.Comment;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.ArticleRepository;
import com.CommuVerse.CommuVerse_api.repository.CommentRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentDTO addComment(CommentDTO dto) {
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = commentMapper.toEntity(dto);
        comment.setArticle(article);
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    @Transactional
    public List<CommentDTO> getCommentsByArticle(Integer articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(commentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO editComment(Integer commentId, CommentDTO dto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Verificar si el comentario está dentro de los 10 minutos permitidos
        if (Duration.between(existingComment.getCreatedAt(), LocalDateTime.now()).toMinutes() > 10) {
            throw new RuntimeException("Time limit for editing the comment has passed");
        }

        existingComment.setContent(dto.getContent());
        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toDTO(updatedComment);
    }

    @Transactional
    public void deleteComment(Integer commentId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Verificar si el comentario está dentro de los 10 minutos permitidos
        if (Duration.between(existingComment.getCreatedAt(), LocalDateTime.now()).toMinutes() > 10) {
            throw new RuntimeException("Time limit for deleting the comment has passed");
        }

        commentRepository.delete(existingComment);
    }
}