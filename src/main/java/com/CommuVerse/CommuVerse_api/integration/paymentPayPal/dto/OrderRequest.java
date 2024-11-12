package com.CommuVerse.CommuVerse_api.integration.paymentPayPal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private String intent;

    @JsonProperty("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @JsonProperty("application_context")
    private ApplicationContext applicationContext;
}