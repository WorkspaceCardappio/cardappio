package br.com.cardappio.domain.ticket;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

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
}
