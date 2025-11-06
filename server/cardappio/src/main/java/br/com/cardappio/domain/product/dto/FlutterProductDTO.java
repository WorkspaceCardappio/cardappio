package br.com.cardappio.domain.product.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FlutterProductDTO(UUID idProductItem, UUID idProduct, String name, BigDecimal price,
                                String description,
                                String note, String image) {
}
