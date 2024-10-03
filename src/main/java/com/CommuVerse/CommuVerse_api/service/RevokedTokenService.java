package com.CommuVerse.CommuVerse_api.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RevokedTokenService {

    private Set<String> revokedTokens = new HashSet<>();

    // Método para revocar un token
    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

    // Método para verificar si un token ha sido revocado
    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
