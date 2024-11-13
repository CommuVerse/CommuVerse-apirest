package com.CommuVerse.CommuVerse_api.integration.paymentPayPal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Amount {
    @JsonProperty("currency_code")
    private String currencyCode;
    private String value;
}