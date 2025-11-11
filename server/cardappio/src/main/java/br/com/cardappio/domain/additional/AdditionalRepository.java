package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.FlutterAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, UUID> {

    @Query("""
            SELECT new br.com.cardappio.domain.order.dto.ProductAdditionalDTO(
                product.id, product.name, items.size, items.price, items.id
            )
            FROM Additional additional
            INNER JOIN additional.product product
            INNER JOIN product.items items
            WHERE product.id = :productId
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
