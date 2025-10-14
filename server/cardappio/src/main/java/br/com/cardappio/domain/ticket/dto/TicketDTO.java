package br.com.cardappio.domain.ticket.dto;

import java.util.UUID;

import br.com.cardappio.domain.person.dto.PersonIdDTO;
import br.com.cardappio.domain.table.dto.TableRestaurantIdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record TicketDTO(

        UUID id,

        @NotNull(message = Messages.CODE_NOT_FOUND)
        Long code,

        @NotNull(message = Messages.PERSON_NON_NULL)
        PersonIdDTO owner,

        @NotNull(message = Messages.TABLE_NON_NULL)
        TableRestaurantIdDTO table
) {}
