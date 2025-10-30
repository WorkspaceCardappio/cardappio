package br.com.cardappio.domain.product;

import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.domain.product.dto.ProductIngredientDTO;
import br.com.cardappio.utils.Messages;
import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "product_ingredient")
public class ProductIngredient implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public static ProductIngredient of(final ProductIngredientDTO dto) {

        final ProductIngredient productIngredient = new ProductIngredient();
        productIngredient.setId(dto.id());
        productIngredient.setProduct(Product.of(dto.product()));
        productIngredient.setIngredient(Ingredient.of(dto.ingredient().id()));

        return productIngredient;
    }
}
