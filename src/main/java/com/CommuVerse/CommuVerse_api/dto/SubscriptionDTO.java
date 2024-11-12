package com.CommuVerse.CommuVerse_api.dto;

import java.time.LocalDateTime;
import com.CommuVerse.CommuVerse_api.model.enums.PaymentStatus;
import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionStatus;

import lombok.Data;


@Data 
public class SubscriptionDTO {

    private Integer id; 
    private Integer userId;
    private Integer subscriptionPlanId; 
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private PaymentStatus paymentStatus;
    private SubscriptionStatus subscriptionStatus;
}
