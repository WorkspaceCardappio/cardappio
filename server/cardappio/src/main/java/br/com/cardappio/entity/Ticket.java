package br.com.cardappio.entity;

import java.util.UUID;

import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.DTO.TicketDTO;
import br.com.cardappio.converter.TicketStatusConverter;
import br.com.cardappio.enums.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

    @Column
    @Convert(converter = TicketStatusConverter.class)
    private TicketStatus status = TicketStatus.OPEN;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person owner;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private TableRestaurant table;

    public static Ticket of(final TicketDTO dto) {

        // TODO: OWNER E TABLE

        final Ticket ticket = new Ticket();
        ticket.setId(dto.id());
        ticket.setNumber(dto.number());

        return ticket;
    }

}
