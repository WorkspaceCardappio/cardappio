package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.utils.EnumDTO;

public record OrderToTicketDTO(

        UUID id,
        Long number,
        BigDecimal total,
        EnumDTO status,
        LocalDateTime createdAt
) {

    public OrderToTicketDTO(final Order order) {
        this(
                order.getId(),
                order.getNumber(),
                order.getTotal(),
                order.getStatus().toDTO(),
                order.getCreatedAt()
        );
    }

}
