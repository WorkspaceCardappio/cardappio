package br.com.cardappio.domain.order.variable.dto;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.order.ProductOrderVariable;
import br.com.cardappio.utils.Messages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderVariableDTO(

        UUID id,

        @NotNull(message = Messages.ORDER_NOT_NULL)
        UUID order,

        @NotNull(message = Messages.ORDER_VARIABLE_NOT_NULL)
        UUID variable,

        @Min(value = 1, message = Messages.MIN_VALUE_ONE)
        @NotNull(message = Messages.QUANTITY_NOT_NULL)
        BigDecimal quantity

) {

    public OrderVariableDTO(final ProductOrderVariable order) {
        this(null, order.getProductOrder().getId(), null, null);
    }

}
