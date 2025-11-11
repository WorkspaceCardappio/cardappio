package br.com.cardappio.domain.order;

import br.com.cardappio.converter.OrderStatusConverter;
import br.com.cardappio.domain.order.dto.FlutterCreateOrderDTO;
import br.com.cardappio.domain.order.dto.OrderDTO;
import br.com.cardappio.domain.save.SaveStatus;
import br.com.cardappio.domain.ticket.Ticket;
import br.com.cardappio.enums.OrderStatus;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@ToString(of = {"id", "number", "status"})
public class Order implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(insertable = false, updatable = false)
    private Long number;

    private BigDecimal total;

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
    private List<ProductOrder> productOrders = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "save_status", nullable = false, length = 10)
    private SaveStatus saveStatus = SaveStatus.DRAFT;

    @Column
    private String note;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public static Order of(final OrderDTO dto) {

        final Order order = new Order();
        order.setId(dto.id());
        order.setStatus(OrderStatus.fromCode(dto.status().code()));
        order.setTicket(Ticket.of(dto.ticket().id()));
        order.setTotal(BigDecimal.ONE);

        return order;
    }

    public static Order of(final UUID id) {
        final Order order = new Order();
        order.setId(id);
        return order;
    }

    public static Order of(final FlutterCreateOrderDTO dto) {

        Order order = new Order();
        order.setStatus(OrderStatus.fromCode(dto.status().code()));
        order.setTicket(Ticket.of(dto.ticket().id()));
        order.setTotal(BigDecimal.ONE);

        return order;
    }

    public void markAsFinalized() {
        saveStatus = SaveStatus.FINALIZED;
    }

}
