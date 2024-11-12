package com.CommuVerse.CommuVerse_api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentOrderResponse {
    private String paypalUrl;
}
