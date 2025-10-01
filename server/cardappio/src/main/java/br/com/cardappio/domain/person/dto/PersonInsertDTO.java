package br.com.cardappio.domain.person.dto;

import br.com.cardappio.domain.address.dto.AddressDTO;
import br.com.cardappio.enums.PersonType;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInsertDTO {

    @NotBlank(message = Messages.EMPTY_NAME)
    @Size(max = 255)
    private String name;

    @NotNull
    private PersonType type;

    @Size(max = 14)
    private String document;

    @Size(max = 11)
    private String phone;

    @NotBlank(message = Messages.EMPTY_PASSWORD)
    @Size(max = 255)
    private String password;

    @Email(message = Messages.INVALID_EMAIL)
    @Size(max = 255)
    private String email;

    private Boolean active = true;

    @NotNull
    private AddressDTO address;
}
