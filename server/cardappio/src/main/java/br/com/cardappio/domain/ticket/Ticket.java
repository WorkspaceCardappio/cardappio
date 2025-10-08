package br.com.cardappio.domain.ticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.converter.TicketStatusConverter;
import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.order.ProductOrder;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.enums.TicketStatus;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Ticket implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String number;

    @Column(nullable = false)
    @NotNull(message = Messages.MIN_VALUE_ZERO)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal total = BigDecimal.ZERO;

    @Column
    @Convert(converter = TicketStatusConverter.class)
    private TicketStatus status = TicketStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private TableRestaurant table;

    @JsonIgnoreProperties("ticket")
    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public static Ticket of(final TicketDTO dto) {

        final Ticket ticket = new Ticket();
        ticket.setId(dto.id());
        ticket.setNumber(dto.number());
        ticket.setOwner(Person.of(dto.owner().id()));
        ticket.setTable(TableRestaurant.of(dto.table().id()));

        return ticket;
    }

}
