package com.CommuVerse.CommuVerse_api.dto;

import lombok.Data;

@Data
public class PaymentCaptureResponse {
    private boolean completed;
    private Integer subscriptionId;
}
