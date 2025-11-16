package br.com.cardappio.domain.order.dto;

import br.com.cardappio.domain.order.item.dto.FlutterCreateOrderItemAdditionalDTO;

import java.util.List;
import java.util.UUID;

public record FlutterCreateOrderItemDTO(
        UUID productId,
        int quantity,
        Double lineTotal,
        List<UUID> variablesId,
        String observations,
        List<FlutterCreateOrderItemAdditionalDTO> additionals
) {
}
