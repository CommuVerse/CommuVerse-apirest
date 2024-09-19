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
public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private Integer articleId;
    private Integer userId;
}