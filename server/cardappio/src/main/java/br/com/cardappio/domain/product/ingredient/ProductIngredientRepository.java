package br.com.cardappio.domain.product.ingredient;

import br.com.cardappio.domain.product.ProductIngredient;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductIngredientRepository extends CrudRepository<ProductIngredient, UUID> {
}
