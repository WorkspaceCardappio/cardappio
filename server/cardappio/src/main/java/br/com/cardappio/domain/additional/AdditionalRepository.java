package br.com.cardappio.domain.additional;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;

@Repository
public interface AdditionalRepository extends JpaRepository<Additional, UUID> {

    @Query("""
            SELECT NEW br.com.cardappio.domain.order.dto.ProductAdditionalDTO(
                product.id, product.name, items.size, items.price
            )
            FROM Additional additional
            INNER JOIN add.product product
            INNER JOIN product.items items
            WHERE product.id = :productId
            """)
    List<ProductAdditionalDTO> findByProductToOrder(@Param("productId") UUID productId);

}
