package br.com.cardappio.service;

import br.com.cardappio.DTO.IngredientDTO;
import br.com.cardappio.adapter.IngredientAdapter;
import br.com.cardappio.entity.Ingredient;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IngredientService extends CrudService<Ingredient, IngredientDTO, UUID> {

    @Override
    protected Adapter<IngredientDTO, Ingredient> getAdapter() {

        return new IngredientAdapter();
    }
}
