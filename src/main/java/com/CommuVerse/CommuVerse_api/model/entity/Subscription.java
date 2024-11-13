package com.CommuVerse.CommuVerse_api.model.entity;

import com.CommuVerse.CommuVerse_api.model.enums.PaymentStatus; 
import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime; // Asegúrate de importar LocalDateTime correctamente
import lombok.Data;

@Entity
@Data
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne 
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne 
    @JoinColumn(name = "subscription_plan_id", nullable = false)
    private SubscriptionPlan subscriptionPlan;
    
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt; 

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;

}
