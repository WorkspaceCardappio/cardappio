package br.com.cardappio.repository;

import br.com.cardappio.entity.Ingredient;
import com.cardappio.core.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, UUID> {
}
