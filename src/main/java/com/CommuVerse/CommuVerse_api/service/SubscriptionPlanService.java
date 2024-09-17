package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionPlanDTO;
import com.CommuVerse.CommuVerse_api.mapper.SubscriptionPlanMapper;
import com.CommuVerse.CommuVerse_api.model.entity.SubscriptionPlan;
import com.CommuVerse.CommuVerse_api.repository.SubscriptionPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;

    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO dto) {
        SubscriptionPlan subscriptionPlan = subscriptionPlanMapper.toEntity(dto);
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(subscriptionPlan);
        return subscriptionPlanMapper.toDTO(savedPlan);
    }
}