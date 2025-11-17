package br.com.cardappio.domain.order.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;

public record ChangeStatusDTO(

        @NotNull(message = Messages.CODE_NOT_FOUND)
        Long code

) {
}
