package br.com.cardappio.domain.address.dto;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDTO(

        UUID id,

        @NotBlank(message = Messages.EMPTY_STREET)
        @Length(max = 255, message = Messages.SIZE_255)
        String street,

        @NotBlank(message = Messages.EMPTY_ZIP_CODE)
        @Length(max = 8, message = Messages.SIZE_8)
        String zipCode,

        @NotBlank(message = Messages.EMPTY_DISTRICT)
        @Length(max = 255, message = Messages.SIZE_255)
        String district,

        @NotBlank(message = Messages.EMPTY_NUMBER)
        @Length(max = 10, message = Messages.SIZE_10)
        String number,

        Boolean active,

        @NotNull(message = Messages.CITY_NON_NULL)
        CityIdDTO city
) {}
