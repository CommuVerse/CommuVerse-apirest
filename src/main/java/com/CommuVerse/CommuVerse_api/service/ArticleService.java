package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.exception.ResourceNotFoundException;
import com.CommuVerse.CommuVerse_api.mapper.ArticleMapper;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.Tag;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.ArticleRepository;
import com.CommuVerse.CommuVerse_api.repository.TagRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final TagRepository tagRepository;  // Inyección del repositorio de etiquetas

    @Transactional
    public ArticleDTO createArticle(ArticleDTO dto) {
        User creator = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleMapper.toEntity(dto);
        article.setCreator(creator);

        Article savedArticle = articleRepository.save(article);
        return articleMapper.toDTO(savedArticle);
    }

    public List<ArticleDTO> searchArticlesByKeyword(String keyword) {
        List<Article> articles = articleRepository.searchByKeyword(keyword);
        return articles.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleDTO editArticle(Integer articleId, ArticleDTO dto) {
        Article existingArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        existingArticle.setTitle(dto.getTitle());
        existingArticle.setContent(dto.getContent());
        Article updatedArticle = articleRepository.save(existingArticle);

        return articleMapper.toDTO(updatedArticle);
    }

    public ArticleDTO assignTagsToArticle(Integer articleId, List<String> tagNames) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        // Asignar etiquetas al artículo
        Set<Tag> tagsToAssign = tagNames.stream().map(tagName -> {
            Tag tag = tagRepository.findByNombreEtiqueta(tagName).orElseGet(() -> {
                // Crear nueva etiqueta si no existe
                Tag newTag = new Tag();
                newTag.setNombreEtiqueta(tagName);
                return tagRepository.save(newTag);
            });
            return tag;
        }).collect(Collectors.toSet());

        article.getTags().addAll(tagsToAssign);
        articleRepository.save(article);

        return articleMapper.toDTO(article);
    }
}
