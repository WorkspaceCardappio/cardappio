package br.com.cardappio.DTO;

import br.com.cardappio.entity.Address;
import br.com.cardappio.entity.City;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

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
        boolean active,
        @NotNull
        City city
) {
    public AddressDTO(final Address address) {
        this(
                address.getId(),
                address.getStreet(),
                address.getZipCode(),
                address.getDistrict(),
                address.getNumber(),
                address.getActive(),
                address.getCity()
        );
    }
}