package br.com.cardappio.domain.additional.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AdditionalDTO(

        UUID id,

        String name,
        
        UUID product,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        IdDTO productAdditional,

        Boolean active
) {
}
