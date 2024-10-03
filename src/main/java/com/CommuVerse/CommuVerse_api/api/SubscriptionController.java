package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.config.JwtUtil;
import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO; 
import com.CommuVerse.CommuVerse_api.service.SubscriptionService; 
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions") 
@RequiredArgsConstructor 
public class SubscriptionController {

    private final SubscriptionService subscriptionService; 
    private final JwtUtil jwtUtil; 

    @PostMapping 
    public ResponseEntity<SubscriptionDTO> createSubscription(
            @RequestHeader("Authorization") String authHeader, 
            @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
        
        String token = authHeader.substring(7);
        String nickname = jwtUtil.extractUsername(token); 
    
        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SubscriptionDTO createdSubscription = subscriptionService.createSubscription(subscriptionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubscription); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscriptionPlan(
            @RequestHeader("Authorization") String authHeader, 
            @PathVariable Integer id,
            @Valid @RequestBody SubscriptionDTO subscriptionDTO) {
    
        String token = authHeader.substring(7); 
        String nickname = jwtUtil.extractUsername(token); 
    
        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    
        // Actualizar el plan de suscripción
        SubscriptionDTO updatedSubscription = subscriptionService.updateSubscriptionPlan(id, subscriptionDTO);
        
        return ResponseEntity.ok(updatedSubscription); 
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSubscriptionsByUserId(
            @RequestHeader("Authorization") String authHeader, 
            @PathVariable Integer userId) {
        
        String token = authHeader.substring(7);
        String nickname = jwtUtil.extractUsername(token);

        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<SubscriptionDTO> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
    
        if (subscriptions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay suscripciones activas para este usuario.");
        }
    
        return ResponseEntity.ok(subscriptions);
    }
    
    @DeleteMapping("/user/{userId}/{subscriptionId}")
    public ResponseEntity<String> cancelSubscription(
            @RequestHeader("Authorization") String authHeader, 
            @PathVariable Integer userId,
            @PathVariable Integer subscriptionId) {

        String token = authHeader.substring(7); 
        String nickname = jwtUtil.extractUsername(token); 

        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            subscriptionService.cancelSubscription(userId, subscriptionId);
            return ResponseEntity.ok("Suscripción cancelada con éxito.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al cancelar la suscripción: " + e.getMessage());
        }
    }



}
    