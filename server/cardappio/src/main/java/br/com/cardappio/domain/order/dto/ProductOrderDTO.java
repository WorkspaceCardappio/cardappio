package br.com.cardappio.domain.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductOrderDTO(
        UUID id,
        UUID orderId,
        UUID productId,
        BigDecimal quantity,
        String note
) {
    /**
     * Este é o construtor que o Jackson usará para criar o DTO a partir do JSON. As anotações @JsonProperty garantem que o mapeamento seja
     * feito corretamente. A anotação @JsonCreator marca este construtor como o ponto de entrada para o JSON.
     */
    @JsonCreator
    public ProductOrderDTO(
            @JsonProperty("productId") @NotNull(message = Messages.PRODUCT_NOT_NULL) UUID productId,
            @JsonProperty("quantity") @NotNull(message = "A quantidade não pode ser nula.") @Positive(message = "A quantidade deve ser um número positivo.") BigDecimal quantity,
            @JsonProperty("note") String note
    ) {
        // Chamamos o construtor principal do record, preenchendo os campos que não vêm do JSON com null.
        this(null, null, productId, quantity, note);
    }

    /**
     * Este é o construtor auxiliar para converter uma Entidade para DTO. O Jackson vai ignorar este construtor para o JSON por causa do
     * @JsonCreator acima.
     */
    public ProductOrderDTO(final ProductOrder productOrder) {
        this(
                productOrder.getId(),
                productOrder.getOrder().getId(),
                productOrder.getProduct().getId(),
                productOrder.getQuantity(),
                productOrder.getNote()
        );
    }
}