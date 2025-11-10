package br.com.cardappio.domain.product.ingredient;

import br.com.cardappio.domain.product.ingredient.dto.IngredientDTO;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductIngredientRepository extends CrudRepository<ProductIngredient, UUID> {

    @Query("""
            SELECT new br.com.cardappio.domain.product.ingredient.dto.IngredientDTO(
                ingredient.id, ingredient.name, ingredient.unityOfMeasurement
            )
            FROM ProductIngredient productIngredient
            INNER JOIN productIngredient.ingredient ingredient
            WHERE productIngredient.product.id = :productId
            """)
    List<IngredientDTO> findIngredientsByProductId(@Param("productId") UUID productId);


}
