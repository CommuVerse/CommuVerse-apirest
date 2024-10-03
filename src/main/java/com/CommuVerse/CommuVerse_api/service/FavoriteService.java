
package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.FavoriteDTO;
import com.CommuVerse.CommuVerse_api.mapper.FavoriteMapper;
import com.CommuVerse.CommuVerse_api.model.entity.Favorite;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.FavoriteRepository;
import com.CommuVerse.CommuVerse_api.repository.ArticleRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository,
                           UserRepository userRepository,
                           ArticleRepository articleRepository,
                           FavoriteMapper favoriteMapper) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.favoriteMapper = favoriteMapper;
    }

    public FavoriteDTO addFavorite(Integer readerId, Integer articleId) {
        Optional<User> userOptional = userRepository.findById(readerId);
        Optional<Article> articleOptional = articleRepository.findById(articleId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User with ID " + readerId + " not found");
        }
        if (articleOptional.isEmpty()) {
            throw new RuntimeException("Article with ID " + articleId + " not found");
        }

        User user = userOptional.get();
        Article article = articleOptional.get();

        Favorite favorite = new Favorite();
        favorite.setReader(user);
        favorite.setArticle(article);
        favorite.setDateSaved(LocalDateTime.now());

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return favoriteMapper.toDTO(savedFavorite);
    }
    public void deleteFavorite(Integer id) {
        Optional<Favorite> favoriteOptional = favoriteRepository.findById(id);
        if (favoriteOptional.isEmpty()) {
            throw new RuntimeException("Artículo con ID " + id + " no encontrado en favoritos");
        }
        favoriteRepository.deleteById(id);
    }
    
    public List<FavoriteDTO> getFavoritesByReaderId(Integer readerId) {
    Optional<User> userOptional = userRepository.findById(readerId);

    if (userOptional.isEmpty()) {
        throw new RuntimeException("User with ID " + readerId + " not found");
    }

    User user = userOptional.get();
    List<Favorite> favorites = favoriteRepository.findByReader(user);
    
    return favorites.stream()
            .map(favoriteMapper::toDTO)
            .collect(Collectors.toList());
    }
}