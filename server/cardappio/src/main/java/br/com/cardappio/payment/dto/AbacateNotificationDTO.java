package br.com.cardappio.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AbacateNotificationDTO(
        String id,
        String type,
        String event,
        @JsonProperty("data")
        NotificationData data
) {
    public record NotificationData(
            String id,
            String status,
            String externalId,
            String amount,
            @JsonProperty("customer_tax_id")
            String customerTaxId
    ) {}
}