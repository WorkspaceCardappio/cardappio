package br.com.cardappio.enums.table.status;

import br.com.cardappio.enums.dto.EnumCodigoDescricaoDTO;
import br.com.cardappio.interfaces.CardappioEnum;
import br.com.cardappio.utils.Messages;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum TableStatus implements CardappioEnum {

    FREE(1L, "Livre"),
    RESERVED(2L, "Reservada"),
    OCCUPIED(3L, "Ocupada"),
    UNAVAILABLE(4L, "Indisponível");

    private final Long code;
    private final String description;

    private static final Map<Long, TableStatus> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(TableStatus::getCode, Function.identity()));

    public static TableStatus fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }

    public static List<EnumCodigoDescricaoDTO> valuesToDTO() {

        return Arrays.stream(values())
                .map(EnumCodigoDescricaoDTO::new)
                .toList();
    }


    @JsonCreator
    public static TableStatus fromValue(EnumCodigoDescricaoDTO enumCodigoDescricaoDTO) {

        return fromCode(enumCodigoDescricaoDTO.code());

    }

}
