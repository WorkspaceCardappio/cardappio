package br.com.cardappio.domain.product.item;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.domain.product.item.dto.ProductItemIngredientDTO;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Table(name = "product_item_ingredient")
public class ProductItemIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = Messages.PRODUCT_NOT_NULL)
    @JoinColumn(name = "product_item_id", nullable = false)
    private ProductItem item;

    @ManyToOne
    @NotNull(message = Messages.INGREDIENT_NOT_NULL)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @NotNull
    @Column(nullable = false)
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;

    public static ProductItemIngredient of(final ProductItemIngredientDTO dto, final ProductItem productItem) {

        final ProductItemIngredient item = new ProductItemIngredient();
        item.setId(dto.id());
        item.setItem(productItem);
        item.setIngredient(Ingredient.of(dto.ingredient().id()));
        item.setQuantity(dto.quantity());

        return item;
    }

}
