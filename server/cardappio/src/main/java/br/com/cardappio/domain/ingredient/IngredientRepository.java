package br.com.cardappio.domain.ingredient;

import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, UUID> {
}
