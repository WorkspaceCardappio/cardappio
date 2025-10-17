package br.com.cardappio.domain.restaurant.dto;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestaurantInsertDTO(

        @NotBlank(message = Messages.EMPTY_NAME)
        @Size(max = 255, message = Messages.SIZE_255)
        String name,

        Boolean active,

        @NotBlank(message = Messages.EMPTY_DOCUMENT)
        @Size(max = 14, message = Messages.SIZE_14)
        String cnpj

) {
}
