package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.ArticleRankingDTO;
import com.CommuVerse.CommuVerse_api.dto.RankingDTO;
import com.CommuVerse.CommuVerse_api.exception.ResourceNotFoundException;
import com.CommuVerse.CommuVerse_api.mapper.RankingMapper;
import com.CommuVerse.CommuVerse_api.model.entity.Article;
import com.CommuVerse.CommuVerse_api.model.entity.Ranking;
import com.CommuVerse.CommuVerse_api.model.entity.User;
import com.CommuVerse.CommuVerse_api.repository.ArticleRepository;
import com.CommuVerse.CommuVerse_api.repository.RankingRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final RankingMapper rankingMapper;

    @Transactional
    public RankingDTO rateArticle(RankingDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        Ranking ranking = rankingRepository.findByUserIdAndArticleId(dto.getUserId(), dto.getArticleId())
                .orElse(new Ranking());

        ranking.setUser(user);
        ranking.setArticle(article);
        ranking.setStars(dto.getStars());

        Ranking savedRanking = rankingRepository.save(ranking);
        return rankingMapper.toDTO(savedRanking);
    }

    // Nuevo método para calcular el promedio de calificaciones por artículo
    @Transactional(readOnly = true)
    public List<ArticleRankingDTO> getArticlesRanking() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(article -> new ArticleRankingDTO(
                        article.getId(),
                        article.getTitle(),
                        calculateAverageRating(article.getId())))
                .sorted((a1, a2) -> Double.compare(a2.getAverageRating(), a1.getAverageRating()))
                .collect(Collectors.toList());
    }

    // Método para calcular el promedio de las calificaciones de un artículo
    private double calculateAverageRating(Integer articleId) {
        List<Ranking> rankings = rankingRepository.findByArticleId(articleId);
        
        double average = rankings.stream()
                .mapToInt(Ranking::getStars)
                .average()
                .orElse(0.0); // Si no tiene calificaciones, el promedio será 0

        // Redondear a un decimal
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(average));
    }
}

