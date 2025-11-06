package br.com.cardappio.domain.order.item.dto;

import java.util.UUID;

public record FlutterCreateOrderItemAdditionalDTO(
        UUID additionalId,
        int quantity
) {
}
