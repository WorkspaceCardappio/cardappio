package br.com.cardappio.domain.person.dto;

import java.util.UUID;
import br.com.cardappio.enums.PersonType;

public record PersonListDTO(
        UUID id,
        String name,
        PersonType type,
        String document,
        String phone,
        String email,
        Boolean active
) {}
