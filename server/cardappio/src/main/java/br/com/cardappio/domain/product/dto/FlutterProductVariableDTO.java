package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FlutterProductVariableDTO(UUID id, String name, BigDecimal price) {
}
