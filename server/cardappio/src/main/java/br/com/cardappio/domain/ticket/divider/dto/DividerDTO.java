package br.com.cardappio.domain.ticket.divider.dto;

import java.util.List;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DividerDTO(

    @NotNull(message = Messages.CODE_NOT_FOUND)
    IdDTO origin,

    @NotEmpty(message = Messages.EMPTY_ORDERS)
    List<IdDTO> orders,

    IdDTO person
) { }
