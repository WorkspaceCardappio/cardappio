package br.com.cardappio.domain.ticket.dto;

import java.util.UUID;

import br.com.cardappio.domain.table.dto.TableRestaurantIdDTO;
import br.com.cardappio.utils.EnumDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record TicketDTO(

        UUID id,

        @NotNull(message = Messages.EMPTY_STATUS)
        EnumDTO status,

        @NotNull(message = Messages.TABLE_NON_NULL)
        TableRestaurantIdDTO table
) {
}
