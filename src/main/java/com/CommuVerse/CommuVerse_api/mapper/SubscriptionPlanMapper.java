package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionPlanDTO;
import com.CommuVerse.CommuVerse_api.model.entity.SubscriptionPlan;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionPlanMapper {

    private final ModelMapper modelMapper;

    public SubscriptionPlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SubscriptionPlanDTO toDTO(SubscriptionPlan subscriptionPlan) {
        return modelMapper.map(subscriptionPlan, SubscriptionPlanDTO.class);
    }

    public SubscriptionPlan toEntity(SubscriptionPlanDTO subscriptionPlanDTO) {
        return modelMapper.map(subscriptionPlanDTO, SubscriptionPlan.class);
    }
}