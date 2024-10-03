package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.ArticleDTO;
import com.CommuVerse.CommuVerse_api.mapper.ArticleMapper;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.ArticleRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleMapper articleMapper;

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
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return articleMapper.toDTO(article);
    }

    public ArticleDTO getArticleDetails(Integer articleId) {

        return null;
    }

    public boolean deleteArticle(Integer articleId) {
        if (articleRepository.existsById(articleId)) {
            articleRepository.deleteById(articleId);
            return true;
        } else {
            return false;
        }
    }

    public ArticleDTO updateArticle(Integer articleId, ArticleDTO articleDTO) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);

        if (!optionalArticle.isPresent()) {
            return null;
        }

        Article articleToUpdate = optionalArticle.get();

        articleToUpdate.setTitle(articleDTO.getTitle());
        articleToUpdate.setContent(articleDTO.getContent());
        articleToUpdate.setType(articleDTO.getType());
        articleToUpdate.setScheduledDate(articleDTO.getScheduledDate());
        articleToUpdate.setStatus(articleDTO.isStatus());

        Article updatedArticle = articleRepository.save(articleToUpdate);

        return articleMapper.toDTO(updatedArticle);
    }
}
