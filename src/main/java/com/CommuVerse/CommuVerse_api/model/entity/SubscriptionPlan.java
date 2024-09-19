package com.CommuVerse.CommuVerse_api.model.entity;

import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionLevel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "subscription_plans")
public class SubscriptionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique=true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private SubscriptionLevel level;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "renewal_period", nullable = false)
    private String renewalPeriod; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;
}