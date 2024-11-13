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

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;
    private final TagRepository tagRepository;

    @Transactional
    public ArticleDTO createArticle(ArticleDTO dto) {
        User creator = userRepository.findById(dto.getCreatorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        existingArticle.setTitle(dto.getTitle());
        existingArticle.setContent(dto.getContent());
        Article updatedArticle = articleRepository.save(existingArticle);

        return articleMapper.toDTO(updatedArticle);
    }

    public List<ArticleDTO> filterArticlesByType(String type) {
        List<Article> articles = articleRepository.findByType(type);
        return articles.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> filterArticlesByPublicationDate(LocalDate date) {
        List<Article> articles = articleRepository.findByPublicationDateAfter(date.atStartOfDay());
        return articles.stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleDTO getArticle(Integer articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        return articleMapper.toDTO(article);
    }

    @Transactional
    public boolean deleteArticle(Integer articleId) {
        if (articleRepository.existsById(articleId)) {
            articleRepository.deleteById(articleId);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public ArticleDTO updateArticle(Integer articleId, ArticleDTO articleDTO) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        Article updatedArticle = articleRepository.save(article);

        return articleMapper.toDTO(updatedArticle);
    }

    @Transactional
    public ArticleDTO assignTagsToArticle(Integer articleId, List<String> tagNames) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        // Asignar etiquetas al artículo
        Set<Tag> tagsToAssign = tagNames.stream().map(tagName -> {
            Tag tag = tagRepository.findByNombreEtiqueta(tagName).orElseGet(() -> {
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
