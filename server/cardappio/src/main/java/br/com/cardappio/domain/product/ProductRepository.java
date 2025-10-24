package br.com.cardappio.domain.product;

import br.com.cardappio.domain.product.dto.FlutterProductDTO;
import br.com.cardappio.domain.product.dto.ProductItemDTO;
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
            SELECT new br.com.cardappio.domain.product.dto.FlutterProductDTO(p.id, p.name, p.price, p.description, p.note)
            FROM Product p
            JOIN p.category c
            WHERE p.active = true
            AND c.id = :idCategory
            """)
    List<FlutterProductDTO> findFlutterProducts(@Param("idCategory") UUID idCategory);

}
