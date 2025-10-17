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
public enum ItemSize {

    UNIQUE(1L, "Único"),
    SMALL(2L, "Pequeno"),
    MEDIUM(3L, "Médio"),
    LARGE(4L, "Grande");

    private final Long code;
    private final String description;

    private static final Map<Long, ItemSize> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(ItemSize::getCode, Function.identity()));

    public static ItemSize fromCode(final Long code) {
        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
