package com.CommuVerse.CommuVerse_api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") // Asegúrate de configurar esto en tu archivo de propiedades o variables de entorno
    private String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnm123456d3rwqrasfd32314sdgdfghdsdsdasda2323214dtgdqey136er"; // Cambia esto a una clave segura y secreta

    public String generateToken(String nickname) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, nickname);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); // Generar la clave segura

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Agrega la fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
                .signWith(key, SignatureAlgorithm.HS512) // Cambiado a HS512
                .compact();
    }

    public Boolean validateToken(String token, String nickname) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(nickname) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); // Generar la clave segura

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
