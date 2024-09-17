package com.CommuVerse.CommuVerse_api.repository;

import com.CommuVerse.CommuVerse_api.model.entity.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {
    SubscriptionPlan findByName(String name);
}