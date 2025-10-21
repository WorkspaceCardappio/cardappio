package br.com.cardappio.domain.city.dto;

import org.hibernate.validator.constraints.Length;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CityDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        @NotNull(message = Messages.ADDRESS_NON_NULL)
        EntityIdDTO address
) {}
