package br.com.cardappio.domain.product;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductVariableRepository {

//    @Query("""
//            SELECT new br.com.cardappio.domain.product.dto.FlutterProductVariableDTO(pv.id, pv.name, pv.price)
//            FROM ProductVariable pv
//            WHERE pv.product.id = :idProduct
//            AND pv.active = true
//            """)
//    List<FlutterProductVariableDTO> findFlutterProductVariables(@Param("idProduct") UUID idProduct);

}
