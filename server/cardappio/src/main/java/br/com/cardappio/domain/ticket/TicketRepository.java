package br.com.cardappio.domain.ticket;

import br.com.cardappio.domain.ticket.dto.FlutterTicketDTO;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    @Query(value = """
            SELECT t FROM Ticket t
            JOIN FETCH t.orders
            WHERE t.number = :number
            """)
    Optional<Ticket> findByNumberWithOrder(@Param("number") String number);

    @Query(value = """
            SELECT t FROM Ticket t
            JOIN FETCH t.orders
            WHERE t.id = :id
            """)
    Optional<Ticket> findByIdWithOrders(@Param("id") UUID id);

    @Query(value = """
            SELECT new br.com.cardappio.domain.ticket.dto.FlutterTicketDTO(t.total)
            FROM Ticket t
            WHERE t.id = :idTicket
            """)
    FlutterTicketDTO findFlutterTicket(UUID idTicket);
}
