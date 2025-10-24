package br.com.cardappio.domain.additional.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FlutterAdditionalDTO(UUID id, String name, BigDecimal price) {
}
