package com.CommuVerse.CommuVerse_api.mapper;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;
import com.CommuVerse.CommuVerse_api.model.entity.Subscription;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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
        
      
        subscription.setStartDate(LocalDate.now());
        subscription.setStatus("ACTIVE"); 
        
        return subscription;
    }
}
