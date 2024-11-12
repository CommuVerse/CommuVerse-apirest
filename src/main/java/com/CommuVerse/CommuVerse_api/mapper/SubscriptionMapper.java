package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Subscription;
import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionStatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SubscriptionMapper {

    private final ModelMapper modelMapper;

    public SubscriptionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SubscriptionDTO toDto(Subscription subscription) {
        SubscriptionDTO subscriptionDTO = modelMapper.map(subscription, SubscriptionDTO.class);
        return subscriptionDTO;
    }

    // Método para convertir SubscriptionDTO a Subscription
    public Subscription toEntity(SubscriptionDTO subscriptionDTO) {
        Subscription subscription = modelMapper.map(subscriptionDTO, Subscription.class);

        // Configurar la fecha de inicio como LocalDateTime.now() para establecer la hora exacta
        subscription.setStartAt(LocalDateTime.now());  // Asumí que el campo en la entidad es startAt

        // Establecer el estado a "PENDING" (o el estado que desees al crear la suscripción)
        subscription.setSubscriptionStatus(SubscriptionStatus.PENDING);  // Usar el enum

        return subscription;
    }
}

