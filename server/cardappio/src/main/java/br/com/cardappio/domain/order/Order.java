package br.com.cardappio.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.converter.OrderStatusConverter;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "client_order")
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

    @Column(insertable = false, updatable = false)
    private Long number;

    @Column(nullable = false)
    @NotNull(message = Messages.MIN_VALUE_ZERO)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(nullable = false)
    @NotNull(message = Messages.STATUS_NOT_NULL)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status = OrderStatus.PENDING;

    @NotNull(message = Messages.TICKET_NOT_NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("order")
    private List<ProductOrder> products = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public static Order of(final OrderDTO dto) {

        final Order order = new Order();
        order.setId(dto.id());
        order.setPrice(dto.price());
        order.setStatus(dto.orderStatus());
        order.setTicket(Ticket.of(dto.ticketId()));

        List<ProductOrder> productOrders = dto.products().stream()
                .map(ProductOrder::of)
                .toList();

        order.getProducts().addAll(productOrders);
        return order;
    }

    public static Order of(final UUID id) {
        final Order order = new Order();
        order.setId(id);
        return order;
    }
}