package br.com.cardappio.domain.product.item;

import br.com.cardappio.domain.ingredient.Ingredient;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

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

}
