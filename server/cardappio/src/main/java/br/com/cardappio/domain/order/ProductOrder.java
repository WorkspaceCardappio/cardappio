package br.com.cardappio.domain.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.domain.order.item.dto.OrderItemDTO;
import br.com.cardappio.domain.product.ProductItem;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
    @JoinColumn(name = "client_order_id", nullable = false)
    private Order order;

    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem productItem;

    @Column
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    @Column
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price;

    @Column
    private String note;

    @JsonIgnoreProperties("productOrder")
    @OneToMany(mappedBy = "productOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductOrderVariable> variables = new ArrayList<>();

    @JsonIgnoreProperties("productOrder")
    @OneToMany(mappedBy = "productOrder", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductOrderAdditional> additionals = new ArrayList<>();

    public static ProductOrder of(final OrderItemDTO dto) {

        final ProductOrder productOrder = new ProductOrder();
        productOrder.setId(dto.id());
        productOrder.setOrder(Order.of(dto.order()));
        productOrder.setProductItem(ProductItem.of(dto.item().id()));
        productOrder.setQuantity(dto.quantity());
        productOrder.setPrice(dto.price());

        return productOrder;
    }

}
