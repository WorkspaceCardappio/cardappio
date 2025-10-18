package br.com.cardappio.domain.table.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.EnumDTO;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TableRestaurantInsertDTO(
        UUID id,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max = 10, message = Messages.SIZE_10)
        String number,

        EnumDTO status,

        @NotNull(message = Messages.EMPTY_NUMBER)
        @Max(value = 10, message = Messages.SIZE_10)
        Long places
) {
}
