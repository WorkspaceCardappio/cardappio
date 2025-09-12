package br.com.cardappio.DTO;

import br.com.cardappio.entity.Order;
import br.com.cardappio.entity.Product;
import br.com.cardappio.entity.ProductOrder;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProductOrderDTO(
        UUID id,
        @NotNull
        Order order,
        @NotNull
        Product product
) {
    public ProductOrderDTO(final ProductOrder productOrder) {

        this(productOrder.getId(), productOrder.getOrder(), productOrder.getProduct());
    }
}
