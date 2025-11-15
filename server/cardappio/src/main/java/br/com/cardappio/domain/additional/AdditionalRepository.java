package br.com.cardappio.domain.additional;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cardappio.domain.additional.dto.FlutterAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, UUID> {

    @Query("""
            SELECT new br.com.cardappio.domain.order.dto.ProductAdditionalDTO(
                productAdditional.id, productAdditional.name, items.size, items.price, items.id
            )
            FROM Additional additional
            INNER JOIN additional.productAdditional productAdditional
            INNER JOIN productAdditional.items items
            WHERE additional.product.id = :productId
                AND additional.active = true
            """)
    List<ProductAdditionalDTO> findByProductToOrder(@Param("productId") UUID productId);

    @Query("""
            SELECT new br.com.cardappio.domain.additional.dto.FlutterAdditionalDTO(a.id, a.name, a.price)
            FROM Additional a
            WHERE a.product.id = :idProduct
            AND a.active = true
            """)
    List<FlutterAdditionalDTO> findFlutterAdditionals(@Param("idProduct") UUID idProduct);
}
