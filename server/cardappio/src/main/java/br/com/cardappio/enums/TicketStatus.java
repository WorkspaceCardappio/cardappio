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
public enum TicketStatus {

    OPEN(1L, "Aberta"),
    FINISHED(2L, "Finalizada"),
    CANCELED(3L, "Cancelada");

    private final Long code;
    private final String description;

    private static final Map<Long, TicketStatus> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(TicketStatus::getCode, Function.identity()));

    public static TicketStatus fromCode(final Long code) {
        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
