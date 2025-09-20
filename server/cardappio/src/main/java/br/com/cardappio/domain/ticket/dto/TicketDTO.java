package br.com.cardappio.domain.ticket.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max=10, message = Messages.SIZE_10)
        String number,

        @NotNull(message = Messages.CODE_NOT_FOUND)
        Long code,

        @NotNull(message = Messages.PERSON_NON_NULL)
        UUID ownerId,

        @NotNull(message = Messages.TABLE_NON_NULL)
        UUID tableId
) {
    public TicketDTO(final Ticket ticket){
        this(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getStatus().getCode(),
                ticket.getOwner().getId(),
                ticket.getTable().getId()
        );
    }
}
