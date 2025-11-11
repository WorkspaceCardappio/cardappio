package br.com.cardappio.domain.ticket.split.dto;

import java.util.Set;
import java.util.UUID;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotEmpty;

public record SplitOrdersDTO(

        @NotEmpty(message = Messages.EMPTY_ORDERS)
        Set<UUID> orders,

        UUID ticket
) {
}
