package br.com.cardappio.domain.stock;

import br.com.cardappio.domain.ingredient.IngredientStock;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientStockRepository extends CrudRepository<IngredientStock, UUID> {
}
