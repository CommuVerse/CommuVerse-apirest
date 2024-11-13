package com.CommuVerse.CommuVerse_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class SubscriptionDTO {

    private Integer id; 
    private Integer userId;
    private Integer subscriptionPlanId;
    private String startDate; 
    private String status;
}
