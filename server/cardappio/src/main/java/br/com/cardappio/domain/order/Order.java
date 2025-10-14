package br.com.cardappio.domain.order;

import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.converter.OrderStatusConverter;
import br.com.cardappio.domain.ticket.Ticket;
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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_order")
@EqualsAndHashCode(of = {"id"})
@ToString(of = { "id", "total", "status" })
public class Order implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = Messages.MIN_VALUE_ZERO)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false)
    @NotNull(message = Messages.STATUS_NOT_NULL)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status = OrderStatus.PENDING;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("order")
    private List<ProductOrder> productOrders = new ArrayList<>();

    public static Order of(final OrderDTO dto) {

        final Order order = new Order();
        order.setId(dto.id());
        order.setTotal(dto.total());
        order.setStatus(dto.orderStatus());
        order.getProductOrders().addAll(dto.products());

        return order;
    }

}
