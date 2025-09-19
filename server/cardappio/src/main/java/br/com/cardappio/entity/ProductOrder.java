package br.com.cardappio.entity;

import br.com.cardappio.DTO.ProductOrderDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Table(name = "product_order")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class ProductOrder implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = Messages.ORDER_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public static ProductOrder of(final ProductOrderDTO dto) {

        final ProductOrder productOrder = new ProductOrder();
        productOrder.setId(dto.id());
        productOrder.setOrder(dto.order());
        productOrder.setProduct(dto.product());

        return productOrder;
    }

}
