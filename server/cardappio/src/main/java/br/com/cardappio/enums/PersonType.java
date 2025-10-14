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

    PF ("PF", "Pessoa Física"),
    PJ ("PJ", "Pessoa Jurídica");

    private final String type;
    private final String description;

    private static final Map<String, PersonType> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(PersonType::getType, Function.identity()));

    public static PersonType fromType(final String type) {

        return Optional.ofNullable(CODE_MAP.get(type))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
