package br.com.cardappio.domain.address.dto;

import java.util.UUID;

import br.com.cardappio.domain.address.Address;

public record AddressListDTO(

        UUID id,

        String street,

        String number,

        String district,

        String zipCode,

        Boolean active,

        String city
) {
    public AddressListDTO(final Address address) {
        this(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getDistrict(),
                address.getZipCode(),
                address.getActive(),
                address.getCity().getName()
        );
    }
}
