package br.com.cardappio.entity;

import br.com.cardappio.DTO.OrderDTO;
import br.com.cardappio.converter.OrderStatusConverter;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class Order implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(nullable = false)
    @NotNull
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status = OrderStatus.PENDING;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("order")
    private List<ProductOrder> products = new ArrayList<>();

    public static Order of(final OrderDTO dto) {

        final Order order = new Order();
        order.setId(dto.id());
        order.setPrice(dto.price());
        order.setStatus(dto.orderStatus());
        order.getProducts().addAll(dto.products());

        return order;
    }

}
