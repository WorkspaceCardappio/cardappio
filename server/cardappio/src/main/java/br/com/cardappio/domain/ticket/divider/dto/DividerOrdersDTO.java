package br.com.cardappio.domain.ticket.divider.dto;

import java.util.Set;
import java.util.UUID;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record DividerOrdersDTO(

    @NotEmpty(message = Messages.EMPTY_ORDERS)
    Set<UUID> orders
) { }
