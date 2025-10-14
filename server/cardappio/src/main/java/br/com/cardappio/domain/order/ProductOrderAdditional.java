package br.com.cardappio.domain.order;


import br.com.cardappio.domain.order.dto.ProductOrderAdditionalDTO;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class ProductOrderAdditional implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_order_id")
    private ProductOrder productOrder;

    private BigDecimal price;

    @Column
    private BigDecimal quantity;

    @Column
    private BigDecimal total;

    public static ProductOrderAdditional of(final ProductOrderAdditionalDTO dto){

        final ProductOrderAdditional productOrderAdditional = new ProductOrderAdditional();
        productOrderAdditional.setId(dto.id());
        productOrderAdditional.setProductOrder(dto.productOrder());
        productOrderAdditional.setQuantity(dto.quantity());
        productOrderAdditional.setTotal(dto.total());

        return productOrderAdditional;
    }
}
