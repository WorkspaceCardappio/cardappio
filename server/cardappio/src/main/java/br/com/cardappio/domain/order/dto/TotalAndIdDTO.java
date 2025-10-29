package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TotalAndIdDTO(

        UUID id,
        BigDecimal total
) {
}