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
public enum State {

    AC(1L, "ACRE"),
    AL(2L, "ALAGOAS"),
    AP(3L, "AMAPÁ"),
    AM(4L, "AMAZONAS"),
    BA(5L, "BAHIA"),
    CE(6L, "CEARÁ"),
    DF(7L, "DISTRITO FEDERAL"),
    ES(8L, "ESPÍRITO SANTO"),
    GO(9L, "GOIÁS"),
    MA(10L, "MARANHÃO"),
    MT(11L, "MATO GROSSO"),
    MS(12L, "MATO GROSSO DO SUL"),
    MG(13L, "MINAS GERAIS"),
    PA(14L, "PARÁ"),
    PB(15L, "PARAÍBA"),
    PR(16L, "PARANÁ"),
    PE(17L, "PERNAMBUCO"),
    PI(18L, "PIAUÍ"),
    RJ(19L, "RIO DE JANEIRO"),
    RN(20L, "RIO GRANDE DO NORTE"),
    RS(21L, "RIO GRANDE DO SUL"),
    RO(22L, "RONDÔNIA"),
    RR(23L, "RORAIMA"),
    SC(24L, "SANTA CATARINA"),
    SP(25L, "SÃO PAULO"),
    SE(26L, "SERGIPE"),
    TO(27L, "TOCANTINS");

    private final Long code;
    private final String description;

    private static final Map<Long, State> CODE_MAP =
            Arrays.stream(values()).collect(Collectors.toMap(State::getCode, Function.identity()));

    public static State fromCode(final Long code) {
        return Optional.ofNullable(CODE_MAP.get(code))
                .orElseThrow(() -> new EntityNotFoundException(Messages.CODE_NOT_FOUND));
    }
}