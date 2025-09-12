package br.com.cardappio.enums;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING(1L, "Pendente"),
    IN_PROGRESS(2L, "Em Andamento"),
    FINISHED(3L, "Finalizado"),
    DELIVERED(4L, "Entregue");

    private final Long code;
    private final String description;

    private static final Map<Long, OrderStatus> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(OrderStatus::getCode, Function.identity()));

    public static OrderStatus fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
