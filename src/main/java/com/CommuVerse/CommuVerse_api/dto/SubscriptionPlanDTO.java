package com.CommuVerse.CommuVerse_api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.math.BigDecimal;
import com.CommuVerse.CommuVerse_api.model.enums.SubscriptionLevel;
import com.CommuVerse.CommuVerse_api.model.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionPlanDTO {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration_days;
    private SubscriptionLevel level;
    private User creator;
}