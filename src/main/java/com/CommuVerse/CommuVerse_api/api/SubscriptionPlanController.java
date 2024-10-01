package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionPlanDTO;
import com.CommuVerse.CommuVerse_api.service.SubscriptionPlanService;

import io.jsonwebtoken.ExpiredJwtException;

import com.CommuVerse.CommuVerse_api.config.JwtUtil; // Asegúrate de importar JwtUtil
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription-plans")
@RequiredArgsConstructor
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;
    private final JwtUtil jwtUtil; // Inyección de JwtUtil

    @PostMapping
    public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(
            @RequestHeader("Authorization") String authHeader, // Obtiene el encabezado de autorización
            @Valid @RequestBody SubscriptionPlanDTO dto) {
        
        String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
        String nickname = jwtUtil.extractUsername(token); // Extraer el nickname del token
    
        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Respuesta 401 si no es válido
        }
    
        SubscriptionPlanDTO createdPlan = subscriptionPlanService.createSubscriptionPlan(dto);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED); 
    }
    

    // Obtener todos los planes de suscripción
    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans() {
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK); 
    }
@GetMapping("{id}")
public ResponseEntity<List<SubscriptionPlanDTO>> getSubscriptionPlansByUserId(
        @PathVariable("id") Integer userId, 
        @RequestHeader("Authorization") String authHeader) { 
    // Verifica que el encabezado no esté vacío
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
    }

    String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
    String nickname;

    try {
        nickname = jwtUtil.extractUsername(token); // Extraer el nickname del token
    } catch (ExpiredJwtException e) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Respuesta 401 si el token ha expirado
    }

    // Validar el token
    if (!jwtUtil.validateToken(token, nickname)) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Respuesta 401 si no es válido
    }

    List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlansByUserId(userId);
    if (plans.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
    }
    return new ResponseEntity<>(plans, HttpStatus.OK); 
}

    @PutMapping("{id}")
    public ResponseEntity<SubscriptionPlanDTO> modifySubscriptionPlan(
            @RequestHeader("Authorization") String authHeader, // Obtiene el encabezado de autorización
            @PathVariable("id") Integer id,
            @Valid @RequestBody SubscriptionPlanDTO dto) {
        
        // Verifica que el encabezado no sea nulo y que tenga el formato adecuado
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
        }
    
        String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
        String nickname = jwtUtil.extractUsername(token); // Extraer el nickname del token
    
        // Validar el token
        if (!jwtUtil.validateToken(token, nickname)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Respuesta 401 si no es válido
        }
    
        // Lógica para modificar el plan de suscripción
        SubscriptionPlanDTO updatedPlan = subscriptionPlanService.modifySubscriptionPlan(id, dto);
        if (updatedPlan == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
public ResponseEntity<Void> deleteSubscriptionPlan(
        @RequestHeader("Authorization") String authHeader, // Obtiene el encabezado de autorización
        @PathVariable("id") Integer id) {
    
    // Verifica que el encabezado no sea nulo y que tenga el formato adecuado
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
    }

    String token = authHeader.substring(7); // Eliminar "Bearer " del encabezado
    String nickname = jwtUtil.extractUsername(token); // Extraer el nickname del token

    // Validar el token
    if (!jwtUtil.validateToken(token, nickname)) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Respuesta 401 si no es válido
    }

    // Lógica para eliminar el plan de suscripción
    boolean isRemoved = subscriptionPlanService.deleteSubscriptionPlan(id);
    if (!isRemoved) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna 404 si no se encuentra el plan
    }

    return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna 204 si se eliminó correctamente
}

    
}