package com.CommuVerse.CommuVerse_api.service.impl;

import com.CommuVerse.CommuVerse_api.repository.SubscriptionRepository;
import com.CommuVerse.CommuVerse_api.repository.UserRepository;
import com.CommuVerse.CommuVerse_api.repository.SubscriptionPlanRepository;
import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;
import com.CommuVerse.CommuVerse_api.exception.ResourceNotFoundException;
import com.CommuVerse.CommuVerse_api.model.entity.Subscription;
import com.CommuVerse.CommuVerse_api.model.enums.PaymentStatus;
import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionStatus;
import com.CommuVerse.CommuVerse_api.mapper.SubscriptionMapper;
import com.CommuVerse.CommuVerse_api.service.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    @Transactional
    public SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO) {
        userRepository.findById(subscriptionDTO.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));
        subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId())
            .orElseThrow(() -> new RuntimeException("Subscription plan not found"));

        // Crear la suscripción en estado "PENDING"
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
        subscription.setSubscriptionStatus(SubscriptionStatus.PENDING);  // Usar el enum
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        return subscriptionMapper.toDto(savedSubscription);
    }

        @Override
    public SubscriptionDTO confirmPayment(Integer id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscription.setPaymentStatus(PaymentStatus.PAID);
        subscription.setSubscriptionStatus(SubscriptionStatus.ACTIVE);
        subscription.setStartAt(LocalDateTime.now());
        subscription.setEndAt(subscription.getStartAt().plusDays(subscription.getSubscriptionPlan().getDuration_days()));
        Subscription updateSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updateSubscription);
    }


    @Override
    @Transactional
    public SubscriptionDTO updateSubscriptionPlan(Integer id, SubscriptionDTO subscriptionDTO) {
        Subscription subscription = subscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId())
            .orElseThrow(() -> new RuntimeException("Subscription plan not found"));

        subscription.setSubscriptionPlan(subscriptionPlanRepository.findById(subscriptionDTO.getSubscriptionPlanId()).get());
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Override
    @Transactional
    public List<SubscriptionDTO> getSubscriptionsByUserId(Integer userId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelSubscription(Integer userId, Integer subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
            .orElseThrow(() -> new RuntimeException("Suscripción no encontrada."));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new RuntimeException("No está autorizado a cancelar esta suscripción.");
        }

        subscription.setSubscriptionStatus(SubscriptionStatus.CANCELLED);  // Usar el enum
        subscriptionRepository.save(subscription);
    }
}
