package com.CommuVerse.CommuVerse_api.integration.paymentPayPal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ApplicationContext {
    @JsonProperty("brand_name")
    private String brandName;

    @JsonProperty("return_url")
    private String returnUrl;

    @JsonProperty("cancel_url")
    private String cancelUrl;
}