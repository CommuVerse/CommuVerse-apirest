package com.CommuVerse.CommuVerse_api.repository;

import com.CommuVerse.CommuVerse_api.model.entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Optional<Ranking> findByUserIdAndArticleId(Integer userId, Integer articleId);

    // Nueva consulta para obtener todas las calificaciones de un artículo
    List<Ranking> findByArticleId(Integer articleId);
}


