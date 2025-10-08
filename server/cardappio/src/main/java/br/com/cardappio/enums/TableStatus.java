package br.com.cardappio.enums;

import br.com.cardappio.utils.Messages;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
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

    private static final Map<String, TableStatus> DESCRIPTION_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(TableStatus::getDescription, Function.identity()));

    public static TableStatus fromCode(final Long code) {

        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }

    public static TableStatus fromString(final String description) {

        return Optional.ofNullable(DESCRIPTION_MAP.get(description))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }

    public static List<EnumCodigoDescricaoDTO> valuesToDTO() {

        return Arrays.stream(values())
                .map(EnumCodigoDescricaoDTO::new)
                .toList();
    }

    public EnumCodigoDescricaoDTO getDto(EnumCodigoDescricaoDTO enumCodigoDescricaoDTO) {
        return new EnumCodigoDescricaoDTO(enumCodigoDescricaoDTO.code(), enumCodigoDescricaoDTO.description());
    }


    @JsonCreator
    public static TableStatus fromValue(EnumCodigoDescricaoDTO enumCodigoDescricaoDTO) {

        return Objects.nonNull(enumCodigoDescricaoDTO.code()) ?
                fromCode(enumCodigoDescricaoDTO.code())
                :
                fromString(enumCodigoDescricaoDTO.description());

    }

}
