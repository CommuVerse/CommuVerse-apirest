package com.CommuVerse.CommuVerse_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RankingDTO {

    private Integer id;
    private Integer stars;
    private Integer userId;
    private Integer articleId;
    private String userName;
}
