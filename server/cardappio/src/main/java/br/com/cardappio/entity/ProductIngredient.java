package br.com.cardappio.entity;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull(message = Messages.EMPTY_PRODUCT)
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = Messages.EMPTY_INGREDIENT)
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @NotNull
    @Column
    @Min(value = 0, message = Messages.MIN_VALUE_ZERO)
    private BigDecimal quantity;
}
