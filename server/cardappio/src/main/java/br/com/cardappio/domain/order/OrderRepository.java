package br.com.cardappio.domain.order;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.order.dto.TotalAndIdDTO;
import br.com.cardappio.domain.save.SaveStatus;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

    Page<Order> findByTicketIdAndSaveStatus(UUID ticketId, SaveStatus saveStatus, Pageable pageable);

    List<Order> findAllBySaveStatus(SaveStatus saveStatus);

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.productOrders po LEFT JOIN FETCH po.productItem pi LEFT JOIN FETCH pi.product WHERE o.saveStatus = :saveStatus")
    List<Order> findAllBySaveStatusWithProductOrders(@Param("saveStatus") SaveStatus saveStatus);

    @Query("""
            SELECT new br.com.cardappio.domain.order.dto.TotalAndIdDTO(
                order.id,
                SUM(
                    (productOrder.quantity * orderItem.price)
                    + COALESCE(poVariable.quantity, 0) * COALESCE(productVariable.price, 0)
                    + COALESCE(poAdditionals.quantity, 0) * COALESCE(itemAdditional.price, 0)
                )
            )
            FROM Order order
            INNER JOIN order.productOrders productOrder
            INNER JOIN productOrder.productItem orderItem
            LEFT JOIN productOrder.variables poVariable
            LEFT JOIN poVariable.productVariable productVariable
            LEFT JOIN productOrder.additionals poAdditionals
            LEFT JOIN poAdditionals.productItem itemAdditional
            WHERE order.id IN (:ids)
            GROUP BY order.id
            """)
    List<TotalAndIdDTO> findTotalByIds(@Param("ids") Set<UUID> ids);

}
