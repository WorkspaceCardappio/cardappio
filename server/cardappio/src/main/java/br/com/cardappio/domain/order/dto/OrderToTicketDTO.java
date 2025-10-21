package br.com.cardappio.domain.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.cardappio.domain.order.Order;
import br.com.cardappio.utils.EnumDTO;

public record OrderToTicketDTO(

        UUID id,
        Long number,
        EnumDTO status,
        LocalDateTime createdAt
) {

    public OrderToTicketDTO(final Order order) {
        this(
                order.getId(),
                order.getNumber(),
                order.getStatus().toDTO(),
                order.getCreatedAt()
        );
    }

}
