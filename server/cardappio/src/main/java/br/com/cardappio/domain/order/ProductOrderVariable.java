package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.variable.dto.OrderVariableDTO;
import br.com.cardappio.domain.variable.ProductVariable;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "quantity"})
@Table(name = "product_order_variable")
@EqualsAndHashCode(of = {"id"})
public class ProductOrderVariable implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id", nullable = false)
    private ProductOrder productOrder;

    @NotNull(message = Messages.PRODUCT_VARIABLE_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variable_id", nullable = false)
    private ProductVariable productVariable;

    @Column
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    public static ProductOrderVariable of(final OrderVariableDTO dto) {

        final ProductOrderVariable order = new ProductOrderVariable();
        order.setId(dto.id());
        order.setQuantity(dto.quantity());
        order.setProductOrder(ProductOrder.of(dto.order()));
        order.setProductVariable(ProductVariable.of(dto.variable()));

        return order;
    }

    public static ProductOrderVariable ofFlutter(UUID id, ProductOrder productOrder) {

        ProductOrderVariable order = new ProductOrderVariable();
        order.setProductVariable(ProductVariable.of(id));
        order.setProductOrder(productOrder);
        order.setQuantity(BigDecimal.ONE);

        return order;
    }

}
