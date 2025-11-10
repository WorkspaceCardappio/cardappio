package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.additional.dto.OrderAdditionalDTO;
import br.com.cardappio.domain.product.item.ProductItem;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "product_order_additional")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class ProductOrderAdditional implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = Messages.PRODUCT_ORDER_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id", nullable = false)
    private ProductOrder productOrder;

    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @Column
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    public static ProductOrderAdditional of(final OrderAdditionalDTO dto) {

        final ProductOrderAdditional order = new ProductOrderAdditional();
        order.setId(dto.id());
        order.setQuantity(dto.quantity());
        order.setProductOrder(ProductOrder.of(dto.order()));
        order.setProductItem(ProductItem.of(dto.item().id()));

        return order;
    }

}
