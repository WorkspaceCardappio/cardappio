package br.com.cardappio.domain.product.ingredient;

import br.com.cardappio.domain.product.Product;
import br.com.cardappio.domain.product.ProductRepository;
import br.com.cardappio.domain.product.dto.ProductIngredientDTO;
import br.com.cardappio.domain.product.dto.ProductIngredientListDTO;
import br.com.cardappio.domain.product.ingredient.adapter.ProductIngredientAdapter;
import br.com.cardappio.domain.product.ingredient.dto.IngredientDTO;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductIngredientService extends CrudService<ProductIngredient, UUID, ProductIngredientListDTO, ProductIngredientDTO> {
    private final ProductRepository productRepository;
    private final ProductIngredientRepository repository;

    @Override
    protected Adapter<ProductIngredient, ProductIngredientListDTO, ProductIngredientDTO> getAdapter() {
        return new ProductIngredientAdapter();
    }

    public void createProductIngredient(@Valid List<ProductIngredientDTO> ingredients) {

        if (ingredients.isEmpty()) {
            return;
        }

        final UUID productId = ingredients.getFirst().product();
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        final List<ProductIngredient> ingredientsToSave = ingredients
                .stream()
                .map(ProductIngredient::of)
                .toList();

        product.getProductIngredients().addAll(ingredientsToSave);

        productRepository.save(product);
    }

    public List<IngredientDTO> findIngredientsByProductId(final UUID productId) {
        return repository.findIngredientsByProductId(productId);
    }
}
