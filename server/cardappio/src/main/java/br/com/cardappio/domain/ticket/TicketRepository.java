package br.com.cardappio.domain.ticket;

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
            SELECT t FROM Ticket t
            LEFT JOIN FETCH t.orders o
            WHERE t.id = :id
            """)
    Optional<Ticket> findByIdWithOrdersFlutter(@Param("id") UUID id);

    Optional<Ticket> findByExternalReferenceId(@Param("externalReferenceId") String externalReferenceId);
}
