package br.com.cardappio.domain.ticket;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.ticket.dto.TotalAndIdDTO;
import br.com.cardappio.enums.TicketStatus;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    Long countByStatus(TicketStatus status);

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

    @Query("""
            SELECT new br.com.cardappio.domain.ticket.dto.TotalAndIdDTO(
                ticket.id,
                SUM(
                    (productOrder.quantity * orderItem.price)
                    + COALESCE(poVariable.quantity, 0) * COALESCE(productVariable.price, 0)
                    + COALESCE(poAdditionals.quantity, 0) * COALESCE(itemAdditional.price, 0)
                )
            )
            FROM Order order
            INNER JOIN order.ticket ticket
            INNER JOIN order.productOrders productOrder
            INNER JOIN productOrder.productItem orderItem
            LEFT JOIN productOrder.variables poVariable
            LEFT JOIN poVariable.productVariable productVariable
            LEFT JOIN productOrder.additionals poAdditionals
            LEFT JOIN poAdditionals.productItem itemAdditional
            WHERE ticket.id IN (:ids)
            AND order.saveStatus = br.com.cardappio.domain.save.SaveStatus.FINALIZED
            GROUP BY ticket.id
            """)
    List<TotalAndIdDTO> findTotalByIds(@Param("ids") Set<UUID> ids);
}
