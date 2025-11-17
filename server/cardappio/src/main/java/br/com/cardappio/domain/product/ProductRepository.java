package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.ProductItemDTO;
import br.com.cardappio.domain.product.item.ProductItem;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    @Query("""
            SELECT pi
            FROM ProductItem pi
            JOIN FETCH pi.product p
            JOIN FETCH p.category c
            WHERE p.active = true
            AND c.id = :idCategory
            """)
    List<ProductItem> findFlutterProductsEntities(@Param("idCategory") UUID idCategory);

    @Query("""
            SELECT p.image
            FROM Product p
            WHERE p.id = :id
            """)
    String findImageById(UUID id);

}