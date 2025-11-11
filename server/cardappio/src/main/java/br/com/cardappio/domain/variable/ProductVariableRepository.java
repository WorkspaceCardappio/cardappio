package br.com.cardappio.domain.variable;

import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import br.com.cardappio.domain.variable.dto.ProductVariableToOrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    @Query("""
            SELECT new br.com.cardappio.domain.product.dto.FlutterProductVariableDTO(pv.id, pv.name, pv.price)
            FROM ProductVariable pv
            WHERE pv.product.id = :idProduct
            AND pv.active = true
            """)
    List<FlutterProductVariableDTO> findFlutterProductVariables(@Param("idProduct") UUID idProduct);

}
