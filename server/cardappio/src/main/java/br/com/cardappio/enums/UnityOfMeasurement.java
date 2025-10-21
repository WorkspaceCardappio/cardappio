package br.com.cardappio.enums;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UnityOfMeasurement {
    LITER(1L, "Litro"),
    MILLILITER(2L, "Mililitro"),
    GRAM(3L, "Grama"),
    KILOGRAM(4L, "Quilograma"),
    UNIT(5L, "Unidade");

    private final Long code;
    private final String description;

    private static final Map<Long, UnityOfMeasurement> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(UnityOfMeasurement::getCode, Function.identity()));

    public static UnityOfMeasurement fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}


