package com.CommuVerse.CommuVerse_api.api;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionPlanDTO;
import com.CommuVerse.CommuVerse_api.service.SubscriptionPlanService;
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

    @PostMapping
    public ResponseEntity<SubscriptionPlanDTO> createSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDTO dto) {
        SubscriptionPlanDTO createdPlan = subscriptionPlanService.createSubscriptionPlan(dto);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED); 
    }

    // Obtener todos los planes de suscripción
    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDTO>> getAllSubscriptionPlans() {
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK); 
    }

    // Obtener todos los planes de suscripción por ID de usuario
    @GetMapping("{id}")
    public ResponseEntity<List<SubscriptionPlanDTO>> getSubscriptionPlansByUserId(@PathVariable("id") Integer userId) {
        List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlansByUserId(userId);
        if (plans.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(plans, HttpStatus.OK); 
    }
}
