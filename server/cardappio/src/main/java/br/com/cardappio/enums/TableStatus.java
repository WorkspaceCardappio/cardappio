package br.com.cardappio.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TableStatus implements EnumSerialize {

    FREE(1L, "Livre"),
    RESERVED(2L, "Reservada"),
    OCCUPIED(3L, "Ocupada"),
    UNAVAILABLE(4L, "Indisponível");

    private static final Map<Long, TableStatus> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(TableStatus::getCode, Function.identity()));
    private final Long code;
    private final String description;

    public static TableStatus fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }

}
