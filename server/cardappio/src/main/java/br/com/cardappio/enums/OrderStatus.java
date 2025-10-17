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
public enum OrderStatus implements EnumSerialize {

    PENDING(1L, "Pendente"),
    IN_PROGRESS(2L, "Em Andamento"),
    FINISHED(3L, "Finalizado"),
    DELIVERED(4L, "Entregue");

    private static final Map<Long, OrderStatus> CODE_MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(OrderStatus::getCode, Function.identity()));

    private final Long code;
    private final String description;

    public static OrderStatus fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}
