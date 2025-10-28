package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductVariableRepository {

    @Query("""
            SELECT new br.com.cardappio.domain.product.dto.FlutterProductVariableDTO(pv.id, pv.name, pv.price)
            FROM ProductVariable pv
            WHERE pv.product.id = :idProduct
            AND pv.active = true
            """)
    List<FlutterProductVariableDTO> findFlutterProductVariables(@Param("idProduct") UUID idProduct);

}
