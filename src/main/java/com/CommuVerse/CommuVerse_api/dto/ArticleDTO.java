package com.CommuVerse.CommuVerse_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleDTO {

    private Integer id;
    private String type;
    private String title;
    private String content;
    private LocalDateTime publicationDate;
    private boolean status;
    private Integer numReads;
    private Integer numComments;
    private Integer numLikes;
    private LocalDateTime scheduledDate;
    private Integer creatorId;
}
