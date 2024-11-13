package com.CommuVerse.CommuVerse_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleRankingDTO {
    private Integer articleId;
    private String articleName;
    private Double averageRating;
}


