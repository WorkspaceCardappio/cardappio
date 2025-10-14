package br.com.cardappio.domain.order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.domain.order.dto.ProductOrderDTO;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.utils.Messages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    @JsonIgnoreProperties("productOrder")
    @OneToMany(mappedBy = "productOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductOrderAdditional> additionals = new ArrayList<>();

    public static ProductOrder of(final ProductOrderDTO dto) {

        final ProductOrder productOrder = new ProductOrder();
        productOrder.setId(dto.id());
        productOrder.setOrder(dto.order());
        productOrder.setProduct(dto.product());

        return productOrder;
    }

}
