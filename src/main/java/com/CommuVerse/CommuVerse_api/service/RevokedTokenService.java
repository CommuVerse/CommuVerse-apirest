package com.CommuVerse.CommuVerse_api.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RevokedTokenService {

    // Conjunto para almacenar tokens revocados
    private final Set<String> revokedTokens = new HashSet<>();

    // Método para revocar un token, guardándolo en el conjunto
    public boolean revokeToken(String token) {
        return revokedTokens.add(token); // Agrega el token a la lista de tokens revocados
    }

    // Método para verificar si un token ya fue revocado
    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token); // Verifica si el token está en la lista de tokens revocados
    }
}
