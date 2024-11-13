package com.CommuVerse.CommuVerse_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDTO {

    private int id;

    @NotBlank(message = "El contenido no puede estar en blanco.")
    @Size(max = 150, message = "El contenido debe ser menor a 150 caracteres")
    private String nombreEtiqueta;

    @NotBlank(message = "El contenido no puede estar en blanco.")
    @Size(min = 200, message = "El contenido debe ser mayor a 200 caracteres")
    private String descripcion;

}
