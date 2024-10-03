package com.CommuVerse.CommuVerse_api.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.CommuVerse.CommuVerse_api.service.RevokedTokenService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}") 
    private String SECRET_KEY;  // El valor será inyectado desde el archivo de configuración

    // Genera un token a partir del nickname del usuario
    public String generateToken(String nickname) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, nickname);
    }

    // Crea el token JWT
    private String createToken(Map<String, Object> claims, String subject) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)  // El subject es el nickname en este caso
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 horas de validez
                .signWith(key, SignatureAlgorithm.HS512) 
                .compact();
    }

    // Valida el token, asegurando que no esté expirado y que el nickname coincida
    public Boolean validateToken(String token, String nickname) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(nickname) && !isTokenExpired(token));
    }

    // Extrae el nickname (subject) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extrae una reclamación específica del token (usado para obtener el subject, la fecha de expiración, etc.)
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrae todas las reclamaciones (claims) del token
    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public Boolean validateToken(String token, String nickname, RevokedTokenService revokedTokenService) {
    final String extractedUsername = extractUsername(token);
    
    if (revokedTokenService.isTokenRevoked(token)) {
        return false;  
    }
    return (extractedUsername.equals(nickname) && !isTokenExpired(token));
}

}
