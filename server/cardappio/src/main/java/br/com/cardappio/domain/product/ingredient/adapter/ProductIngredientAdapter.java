package br.com.cardappio.domain.product.ingredient.adapter;

import br.com.cardappio.domain.product.ProductIngredient;
import br.com.cardappio.domain.product.dto.ProductIngredientDTO;
import br.com.cardappio.domain.product.dto.ProductIngredientListDTO;
import com.cardappio.core.adapter.Adapter;

public class ProductIngredientAdapter implements Adapter<ProductIngredient, ProductIngredientListDTO, ProductIngredientDTO> {
    @Override
    public ProductIngredientListDTO toDTO(ProductIngredient entity) {
        return new ProductIngredientListDTO(entity);
    }

    @Override
    public ProductIngredient toEntity(ProductIngredientDTO productIngredientDTO) {
        return ProductIngredient.of(productIngredientDTO);
    }
}
