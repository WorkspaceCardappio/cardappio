package br.com.cardappio.domain.person.dto;

import java.util.UUID;

import br.com.cardappio.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonListDTO {

    private UUID id;
    private String name;
    private PersonType type;
    private String document;
    private String phone;
    private String email;
    private Boolean active;

}
