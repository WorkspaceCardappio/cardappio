package br.com.cardappio.domain.person.dto;

import br.com.cardappio.domain.address.dto.AddressDTO;
import br.com.cardappio.enums.PersonType;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PersonInsertDTO(

        @NotBlank(message = Messages.EMPTY_NAME)
        @Size(max = 255)
        String name,

        @NotNull
        PersonType type,

        @Size(max = 14)
        String document,

        @Size(max = 11)
        String phone,

        @NotBlank(message = Messages.EMPTY_PASSWORD)
        @Size(max = 255)
        String password,

        @Email(message = Messages.INVALID_EMAIL)
        @Size(max = 255)
        String email,

        Boolean active,

        @NotNull
        AddressDTO address
) {
}
