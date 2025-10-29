package br.com.cardappio.domain.variable;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;

@Repository
public interface ProductVariableRepository extends JpaRepository<ProductVariable, UUID> {

    @Query("""
            SELECT new br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO(
                variable.id, variable.name, variable.price
            )
            FROM ProductVariable variable
            WHERE variable.product.id = :productId
                AND variable.active = true
            """)
    List<ProductVariableToOrderDTO> findByProductToOrder(@Param("productId") UUID productId);

}
