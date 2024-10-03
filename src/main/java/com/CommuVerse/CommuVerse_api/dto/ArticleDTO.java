package com.CommuVerse.CommuVerse_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "El tipo no puede estar en blanco")
    @Size(max = 50, message = "El tipo debe ser menor a 50 caracteres")
    private String type;

    @NotBlank(message = "Titulo no puede estar en blanco")
    @Size(max = 100, message = "El titulo debe ser menor a 100 caracteres")
    private String title;

    @NotBlank(message = "El contenido no puede estar en blanco.")
    @Size(min = 50, message = "El contenido debe ser mayor a 50 caracteres")
    private String content;

    private LocalDateTime publicationDate;

    private boolean status;

    private Integer numReads;

    private Integer numComments;

    private Integer numLikes;

    private LocalDateTime scheduledDate;

    private Integer creatorId;
}
