package br.com.cardappio.domain.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cardappio.domain.order.dto.ProductOrderToSummaryDTO;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, UUID> {

    @Query("""
            SELECT po
                FROM ProductOrder po
                LEFT JOIN FETCH po.additionals
                WHERE po.id = :id
            """)
    Optional<ProductOrder> findWithAdditionalsById(@Param("id") UUID id);

    @Query("""
            SELECT po
                FROM ProductOrder po
                LEFT JOIN FETCH po.variables
                WHERE po.id = :id
            """)
    Optional<ProductOrder> findWithVariablesById(@Param("id") UUID id);

    @Query("""
            SELECT new br.com.cardappio.domain.order.dto.ProductOrderToSummaryDTO(
                po.id, product.name, item.size, po.note,
                po.quantity,
                item.price,
                SUM(COALESCE(item.price, 0) * COALESCE(po.quantity, 0)),
                SUM(COALESCE(productVariable.price, 0) * COALESCE(variables.quantity, 0)) AS priceVariables,
                SUM(COALESCE(itemAdditional.price, 0) * COALESCE(additionals.quantity, 0)) AS priceAdditionals
            )
                FROM ProductOrder po
                INNER JOIN po.productItem item
                INNER JOIN item.product product
                LEFT JOIN po.variables variables
                LEFT JOIN variables.productVariable productVariable
                LEFT JOIN po.additionals additionals
                LEFT JOIN additionals.productItem itemAdditional
                WHERE po.order.id = :id
                GROUP BY po.id, product.name, item.size, po.note, po.quantity, item.price
            """)
    List<ProductOrderToSummaryDTO> findSummaryByOrderId(@Param("id") UUID id);

}
