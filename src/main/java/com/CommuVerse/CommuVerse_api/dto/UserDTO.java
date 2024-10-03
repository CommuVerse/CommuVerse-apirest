package com.CommuVerse.CommuVerse_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private int id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre debe tener 50 caracteres o menos")
    private String name;

    @NotBlank(message = "El nickname es obligatorio")  
    @Size(max = 30, message = "El nickname debe tener 30 caracteres o menos")
    private String nickName;

    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min=8, message = "La contraseña debe ser minimo de 8 caracteres")
    private String password;

    private String bio;
    private Date dateOfBirth;
    private String role;
}
