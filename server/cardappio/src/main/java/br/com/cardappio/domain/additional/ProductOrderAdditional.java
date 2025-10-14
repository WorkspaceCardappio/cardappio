package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.ProductOrderAdditionalDTO;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
        productOrderAdditional.setAdditional(dto.additional());
        productOrderAdditional.setQuantity(dto.quantity());
        productOrderAdditional.setTotal(dto.total());

        return productOrderAdditional;
    }
}
