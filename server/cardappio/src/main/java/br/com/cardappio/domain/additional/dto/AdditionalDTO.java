package br.com.cardappio.domain.additional.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.additional.Additional;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdditionalDTO(

        UUID id,

        @NotBlank
        String name,

        @NotNull
        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        BigDecimal price
) {
    public AdditionalDTO(final Additional additional) {
        this(
                additional.getId(),
                additional.getName(),
                additional.getPrice()
        );
    }
}
