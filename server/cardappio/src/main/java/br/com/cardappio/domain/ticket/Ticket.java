package br.com.cardappio.domain.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cardappio.core.entity.EntityModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.converter.TicketStatusConverter;
import br.com.cardappio.domain.order.Order;
import br.com.cardappio.domain.person.Person;
import br.com.cardappio.domain.table.TableRestaurant;
import br.com.cardappio.domain.ticket.dto.TicketDTO;
import br.com.cardappio.enums.TicketStatus;
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
import jakarta.persistence.Table;
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
@ToString(of = {"id", "number", "status"})
public class Ticket implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(updatable = false, insertable = false)
    private Long number;

    @Column
    @Convert(converter = TicketStatusConverter.class)
    private TicketStatus status = TicketStatus.OPEN;

    // TODO: PEGAR DO AUTHENTICATION
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private TableRestaurant table;

    @JsonIgnoreProperties("ticket")
    @OneToMany(mappedBy = "ticket", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    @Column(name = "external_reference_id", unique = true)
    private String externalReferenceId;

    public static Ticket of(final TicketDTO dto) {

        final Ticket ticket = new Ticket();
        ticket.setId(dto.id());
        ticket.setTable(TableRestaurant.of(dto.table().id()));
        ticket.setStatus(TicketStatus.fromCode(dto.status().code()));

        return ticket;
    }

    public static Ticket of(final UUID id) {

        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;

    }

    public final Ticket cloneByNewTicket() {

        final Ticket ticket = new Ticket();
        ticket.setTable(table);

        return ticket;
    }
}
