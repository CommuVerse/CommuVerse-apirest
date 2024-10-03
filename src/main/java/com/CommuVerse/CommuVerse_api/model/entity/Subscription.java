package com.CommuVerse.CommuVerse_api.model.entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
@Table(name = "subscriptions")
@NoArgsConstructor
@AllArgsConstructor 
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

    private LocalDate startDate; // Fecha de inicio de la suscripción
    private String status; // Estado de la suscripción (por ejemplo, "Activo", "Cancelado")
}
