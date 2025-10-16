package br.com.cardappio.domain.menu.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.utils.IdDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record MenuProductDTO(

        UUID id,

        @NotNull(message = Messages.EMPTY_PRODUCT)
        IdDTO product,

        @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
        @NotNull(message = Messages.EMPTY_PRICE)
        BigDecimal price,

        Boolean active

) {
}
