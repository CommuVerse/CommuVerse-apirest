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

import java.util.List;
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
 // Nuevo método para buscar artículos por palabras clave
 public List<ArticleDTO> searchArticlesByKeyword(String keyword) {
    List<Article> articles = articleRepository.searchByKeyword(keyword);
    return articles.stream()
            .map(articleMapper::toDTO)
            .collect(Collectors.toList());
}

}
