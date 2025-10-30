package br.com.cardappio.domain.product.ingredient;

import br.com.cardappio.domain.product.ProductIngredient;
import br.com.cardappio.domain.product.dto.ProductIngredientDTO;
import br.com.cardappio.domain.product.dto.ProductIngredientListDTO;
import com.cardappio.core.controller.CrudController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/product-ingredients")
@RequiredArgsConstructor
public class ProductIngredientController extends CrudController<ProductIngredient, UUID, ProductIngredientListDTO, ProductIngredientDTO> {

    private final ProductIngredientService service;

    @PostMapping("/ingredients")
    public ResponseEntity<Void> createProductIngredient(@RequestBody @Valid List<ProductIngredientDTO> ingredients) {
        service.createProductIngredient(ingredients);
        return ResponseEntity.ok().build();
    }

}
