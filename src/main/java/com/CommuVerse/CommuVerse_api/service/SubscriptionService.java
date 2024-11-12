package com.CommuVerse.CommuVerse_api.service;

import com.CommuVerse.CommuVerse_api.dto.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDTO createSubscription(SubscriptionDTO subscriptionDTO);

    SubscriptionDTO confirmPayment(Integer subscriptionId);

    SubscriptionDTO updateSubscriptionPlan(Integer id, SubscriptionDTO subscriptionDTO);

    List<SubscriptionDTO> getSubscriptionsByUserId(Integer userId);

    void cancelSubscription(Integer userId, Integer subscriptionId);
}
