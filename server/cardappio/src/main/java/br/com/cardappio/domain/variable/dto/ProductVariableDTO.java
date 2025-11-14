package br.com.cardappio.domain.variable.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductVariableDTO(

        UUID id,

        @NotNull(message = Messages.PRODUCT_NOT_NULL)
        UUID product,

        @NotBlank(message = Messages.EMPTY_NAME)
        String name,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price,

        Boolean active
) {
}
