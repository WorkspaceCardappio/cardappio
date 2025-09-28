package br.com.cardappio.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PersonType {

    PF (1L, "PF", "Pessoa Física"),
    PJ (2L, "PJ", "Pessoa Jurídica");

    private final Long code;
    private final String type;
    private final String description;

    private static final Map<Long, PersonType> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(PersonType::getCode, Function.identity()));

    public static PersonType fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
