package br.com.cardappio.domain.city.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_NAME)
        @Length(max = 255, message = Messages.SIZE_255)
        String name,

        @NotNull(message = Messages.ADDRESS_NON_NULL)
        AddressIdDTO address
) {}

