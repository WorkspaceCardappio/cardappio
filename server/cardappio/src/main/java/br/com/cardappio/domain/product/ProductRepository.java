package br.com.cardappio.domain.product;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cardappio.core.repository.CrudRepository;

import br.com.cardappio.domain.product.dto.ProductItemDTO;

@Repository
public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Query("""
            SELECT new br.com.cardappio.domain.product.dto.ProductItemDTO(
                item.id, item.size, item.price, item.description, item.active
            )
            FROM ProductItem item
            WHERE item.product.id = :id
            """)
    List<ProductItemDTO> findOptionsById(@Param("id") UUID id);

}
