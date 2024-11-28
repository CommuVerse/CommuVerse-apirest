package com.CommuVerse.CommuVerse_api.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String token;    // Token JWT
    private String nickName; // Nickname del usuario
    private Integer id;      // ID del usuario
    private String message;  // Mensaje opcional
}

