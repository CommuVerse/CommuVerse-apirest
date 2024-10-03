package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.repository.SubscriptionRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import com.CommuVerse.CommuVerse_api.repository.SubscriptionPlanRepository;
import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Subscription;
import com.CommuVerse.CommuVerse_api.mapper.SubscriptionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionMapper subscriptionMapper; 

    @Transactional
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        userRepository.findById(subscriptionDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId())
            .orElseThrow(() -> new RuntimeException("Subscription plan not found"));

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(savedSubscription); 
    }

    @Transactional
    public SubscriptionDTO updateSubscriptionPlan(Integer id, SubscriptionDTO subscriptionDTO) {
        // Busca la suscripción existente
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

        // Verifica si el nuevo plan de suscripción existe
        subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId())
            .orElseThrow(() -> new RuntimeException("Subscription plan not found"));

        // Actualiza solo el plan de suscripción
        subscription.setSubscriptionPlan(subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId()).get());

        // Guarda la suscripción actualizada
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription); // Retorna el DTO actualizado
    }
    
    @Transactional
    public List<SubscriptionDTO> getSubscriptionsByUserId(Integer userId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelSubscription(Integer userId, Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
            .orElseThrow(() -> new RuntimeException("Suscripción no encontrada."));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new RuntimeException("No está autorizado a cancelar esta suscripción.");
        }

        subscription.setStatus("CANCELADA");
        subscriptionRepository.save(subscription); 
    }

}

