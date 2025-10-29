package br.com.cardappio.domain.variable.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductVariableToOrderDTO(

        UUID id,

        String name,

        BigDecimal price
) {
}
