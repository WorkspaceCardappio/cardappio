package br.com.cardappio.service;

import br.com.cardappio.DTO.IngredientDTO;
import br.com.cardappio.adapter.IngredientAdapter;
import br.com.cardappio.entity.Ingredient;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientService extends CrudService<Ingredient, UUID, IngredientDTO, IngredientDTO> {

    @Override
    protected Adapter<Ingredient, IngredientDTO, IngredientDTO> getAdapter() {

        return new IngredientAdapter();
    }
}
