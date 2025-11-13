package br.com.cardappio.domain.stock;

import br.com.cardappio.domain.ingredient.IngredientStock;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;

@Repository
public interface IngredientStockRepository extends CrudRepository<IngredientStock, UUID> {

	@Query("SELECT s FROM IngredientStock s WHERE s.ingredient.id = :ingredientId AND s.quantity > 0 ORDER BY s.expirationDate ASC, s.deliveryDate ASC, s.number ASC")
	List<IngredientStock> findAvailableByIngredient(@Param("ingredientId") UUID ingredientId);

	@Query("SELECT COALESCE(SUM(s.quantity),0) FROM IngredientStock s WHERE s.ingredient.id = :ingredientId")
	BigDecimal sumQuantityByIngredient(@Param("ingredientId") UUID ingredientId);
}
